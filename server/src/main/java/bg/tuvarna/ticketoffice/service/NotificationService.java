package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {

    public void create(List<Long> userIds, String message);

    public ResponseEntity<Long> newNotificationCountByUser(User user);

    public ResponseEntity<List<NotificationListResponse>> newNotificationsByUser(User user);
}
