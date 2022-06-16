package bg.tuvarna.ticketoffice.repository.custom.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;
import bg.tuvarna.ticketoffice.domain.entities.Distributor;
import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.repository.custom.DistributorRepositoryCustom;
import bg.tuvarna.ticketoffice.repository.custom.EventRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DistributorRepositoryCustomImpl implements DistributorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<DistributorListResponse> getFilteredDistributors(DistributorListFilterRequest filters, Long organiserId) {
        String hql = "SELECT id, name, rating, soldTickets " +
                "FROM (SELECT " +
                "   tu.id AS id, " +
                "   tu.name AS name, " +
                "   (SELECT avg(r.VALUE) FROM RATING r " +
                "     WHERE r.USER_TO = tu.ID) AS rating, " +
                "   (SELECT count(t.TICKETS_COUNT) FROM TICKET t " +
                "     JOIN EVENT e ON t.EVENT_ID = e.ID " +
                "     WHERE t.DISTRIBUTOR_ID = tu.ID " +
                "      AND e.USER_ID = :organiserId) AS soldTickets " +
                " FROM TICKET_USER tu " +
                " WHERE tu.ROLE = :role) " +
                "WHERE 1 = 1 ";

        List<Consumer<Query>> parameters = new ArrayList<>();

        if (filters.getName() != null) {
            hql += " AND LOWER(name) LIKE LOWER(:name) ";
            parameters.add(tq -> tq.setParameter("name", "%" + filters.getName() + "%"));
        }

        if (filters.getRating() != null) {
            hql += " AND rating >= :rating ";
            parameters.add(tq -> tq.setParameter("rating", filters.getRating()));
        }

        if (filters.getSoldTickets() != null) {
            hql += " AND soldTickets >= :soldTickets ";
            parameters.add(tq -> tq.setParameter("soldTickets", filters.getSoldTickets()));
        }

        hql += " ORDER BY rating DESC, name ";

        Query nativeQuery = entityManager.createNativeQuery(hql, "DistributorListMapping");
        nativeQuery.setParameter("organiserId", organiserId);
        nativeQuery.setParameter("role", Role.DISTRIBUTOR.name());
        parameters.forEach(tq -> tq.accept(nativeQuery));
        return nativeQuery.getResultList();
    }
}
