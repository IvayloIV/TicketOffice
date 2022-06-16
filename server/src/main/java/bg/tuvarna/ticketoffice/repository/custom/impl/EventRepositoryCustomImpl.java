package bg.tuvarna.ticketoffice.repository.custom.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;
import bg.tuvarna.ticketoffice.domain.entities.Distributor;
import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.repository.custom.EventRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventListResponse> getOrganiserEvents(EventListFilterRequest filters, Long organiserId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventListResponse> query = cb.createQuery(EventListResponse.class);
        Root<Event> eventRoot = query.from(Event.class);

        List<Predicate> predicates = createFilters(cb, eventRoot, filters);
        predicates.add(cb.equal(eventRoot.get("user").get("id"), organiserId));

        buildQuery(query, eventRoot, predicates, cb);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<EventListResponse> getDistributorEvents(EventListFilterRequest filters, Long distributorId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventListResponse> query = cb.createQuery(EventListResponse.class);
        Root<Distributor> distributorRoot = query.from(Distributor.class);
        Path<Object> eventPath = distributorRoot.get("id").get("event");

        List<Predicate> predicates = createFilters(cb, eventPath, filters);
        predicates.add(cb.equal(distributorRoot.get("id").get("user").get("id"), distributorId));

        buildQuery(query, eventPath, predicates, cb);
        return entityManager.createQuery(query).getResultList();
    }

    private void buildQuery(CriteriaQuery<EventListResponse> query, Path<?> path, List<Predicate> predicates, CriteriaBuilder cb) {
        query.multiselect(path.get("id"), path.get("type"), path.get("ticketsPerUser"),
                path.get("startDate"), path.get("location"));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(path.get("startDate")));
    }

    private List<Predicate> createFilters(CriteriaBuilder cb, Path<?> path, EventListFilterRequest filters) {
        List<Predicate> predicates = new ArrayList<>();

        if (filters.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(path.get("startDate"), filters.getStartDate()));
        }

        if (filters.getLocation() != null) {
            predicates.add(cb.like(cb.lower(path.get("location")), String.format("%%%s%%", filters.getLocation().toLowerCase())));
        }

        if (filters.getActive() != null) {
            predicates.add(cb.greaterThanOrEqualTo(path.get("startDate"), LocalDateTime.now()));
        }

        return predicates;
    }
}
