package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.domain.entities.Notification;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.entities.UserNotification;
import bg.tuvarna.ticketoffice.domain.entities.UserNotificationId;
import bg.tuvarna.ticketoffice.repository.NotificationRepository;
import bg.tuvarna.ticketoffice.repository.UserNotificationRepository;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.NotificationService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificationServiceImpl(UserNotificationRepository userNotificationRepository,
                                   UserRepository userRepository,
                                   ResourceBundleUtil resourceBundleUtil,
                                   NotificationRepository notificationRepository,
                                   ModelMapper modelMapper) {
        this.userNotificationRepository = userNotificationRepository;
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void create(List<Long> userIds, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification = notificationRepository.save(notification);

        List<UserNotification> notifications = new ArrayList<>();

        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        resourceBundleUtil.getMessage("notificationCreate.userNotFound", userId)));

            UserNotificationId userNotificationId = new UserNotificationId();
            userNotificationId.setNotification(notification);
            userNotificationId.setUser(user);

            UserNotification userNotification = new UserNotification();
            userNotification.setId(userNotificationId);
            userNotification.setSeen(false);
            notifications.add(userNotification);
        }

        userNotificationRepository.saveAll(notifications);
    }

    @Override
    public ResponseEntity<Long> newNotificationCountByUser(User user) {
        return ResponseEntity.ok(userNotificationRepository.countById_User_IdAndSeen(user.getId(), false));
    }

    @Override
    public ResponseEntity<List<NotificationListResponse>> newNotificationsByUser(User user) {
        List<UserNotification> userNotifications = userNotificationRepository.findById_User_IdAndSeen(user.getId(), false);
        List<NotificationListResponse> notificationList = userNotifications.stream()
                .map(un -> {
                    un.setSeen(true);
                    return modelMapper.map(un.getId().getNotification(), NotificationListResponse.class);
                })
                .collect(Collectors.toList());

        userNotificationRepository.saveAll(userNotifications);
        return ResponseEntity.ok(notificationList);
    }
}
