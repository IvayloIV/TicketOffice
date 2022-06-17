package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.domain.entities.Ticket;
import bg.tuvarna.ticketoffice.repository.custom.EventRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    public Long countByType(String type);

    @Query("select e.placesCount - coalesce(sum(t.ticketsCount), 0)" +
            "from Event e " +
            "left join Ticket t on t.event.id = e.id " +
            "where e.id = :id " +
            "group by e.id, e.placesCount")
    public Long leftEventTickets(@Param("id") Long eventId);

    @Query("select coalesce(sum(t.ticketsCount), 0)" +
            "from Event e " +
            "left join Ticket t on t.event.id = e.id " +
            "where e.id = :id ")
    public Long soldEventTickets(@Param("id") Long eventId);

    public List<Event> findByStartDateBetween(LocalDateTime start, LocalDateTime end);

    public List<Event> findByStartDateAfter(LocalDateTime start);
}
