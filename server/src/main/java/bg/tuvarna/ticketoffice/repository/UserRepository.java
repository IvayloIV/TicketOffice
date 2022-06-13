package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByName(String username);

    public boolean existsByNameIgnoreCase(String name);

    public boolean existsByIdAndRole(Long userId, Role role);

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

    @Query("select new bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse( " +
            "   u.id, " +
            "   u.name, " +
            "   u.role, " +
            "   (select sum(t.ticketsCount * t.event.price) from Ticket t " +
            "       where t.user.id = u.id), " +
            "   (select count(d.id.event.id) from Distributor d " +
            "       where d.id.user.id = u.id), " +
            "   (select count(d.id.event.id) from Distributor d " +
            "       where d.id.user.id = u.id " +
            "        and d.id.event.startDate > current_timestamp), " +
            "   (select sum(t.ticketsCount) from Ticket t " +
            "       where t.user.id = u.id), " +
            "   (select avg(r.value) from Rating r " +
            "       where r.id.userTo.id = u.id) " +
            ") " +
            "from User u " +
            " where u.id = :id")
    public UserProfileResponse getDistributorProfile(@Param("id") Long id);
}
