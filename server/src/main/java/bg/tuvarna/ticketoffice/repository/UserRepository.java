package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByName(String username);
}
