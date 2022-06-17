package bg.tuvarna.ticketoffice.aspect;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.service.NotificationService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificationAspect {

    private final NotificationService notificationService;
    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public NotificationAspect(NotificationService notificationService,
                              ResourceBundleUtil resourceBundleUtil) {
        this.notificationService = notificationService;
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @Pointcut("execution(public * bg.tuvarna.ticketoffice.service.EventService.create(..))")
    private void createNotificationPointcut() {
    }

    @AfterReturning(pointcut = "createNotificationPointcut()")
    public void test(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        CreateEventRequest createEventRequest = (CreateEventRequest) args[0];
        User user = (User) args[1];
        String message = resourceBundleUtil
                .getMessage("notificationCreate.newEvent", user.getName(), createEventRequest.getType());
        notificationService.create(createEventRequest.getDistributorIds(), message);
    }
}
