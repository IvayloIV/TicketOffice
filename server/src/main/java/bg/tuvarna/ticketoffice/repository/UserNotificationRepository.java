package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.UserNotification;
import bg.tuvarna.ticketoffice.domain.entities.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {

    public Long countById_User_IdAndSeen(Long userId, boolean seen);

    public List<UserNotification> findById_User_IdAndSeen(Long userId, boolean seen);
}
