package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.Rating;
import bg.tuvarna.ticketoffice.domain.entities.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {

    public Optional<Rating> getById_UserFrom_IdAndId_UserTo_Id(Long userFromId, Long userToId);
}
