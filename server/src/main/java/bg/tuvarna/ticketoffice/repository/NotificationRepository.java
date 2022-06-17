package bg.tuvarna.ticketoffice.repository;

import bg.tuvarna.ticketoffice.domain.entities.Notification;
import bg.tuvarna.ticketoffice.domain.entities.UserNotification;
import bg.tuvarna.ticketoffice.domain.entities.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
