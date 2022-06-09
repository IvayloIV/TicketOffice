package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByName(String username);

    public boolean existsByNameIgnoreCase(String name);

    @Query("select new bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse( " +
            "   u.id, " +
            "   u.name, " +
            "   u.role, " +
            "   (select sum(t.ticketsCount * e.price) from Event e " +
            "       join Ticket t on t.event.id = e.id " +
            "       where e.user.id = u.id), " +
            "   (select count(e.id) from Event e " +
            "       where e.user.id = u.id), " +
            "   (select count(e.id) from Event e " +
            "       where e.user.id = u.id " +
            "        and e.startDate > current_timestamp), " +
            "   (select sum(t.ticketsCount) from Event e " +
            "       join Ticket t on t.event.id = e.id " +
            "       where e.user.id = u.id) " +
            ") " +
            "from User u " +
            " where u.id = :id")
    public UserProfileResponse getOrganiserProfile(@Param("id") Long id);
}