package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.domain.dtos.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> notificationCount(Authentication authentication) {
        return notificationService.newNotificationCountByUser((User) authentication.getPrincipal());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<NotificationListResponse>> notificationList(Authentication authentication) {
        return notificationService.newNotificationsByUser((User) authentication.getPrincipal());
    }
}
