package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.Distributor;
import bg.tuvarna.ticketoffice.domain.entities.DistributorId;
import bg.tuvarna.ticketoffice.repository.custom.DistributorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, DistributorId>, DistributorRepositoryCustom {

    public Optional<Distributor> findById_User_IdAndId_Event_Id(Long userId, Long eventId);

    public List<Distributor> findById_User_Id(Long userId);
}
