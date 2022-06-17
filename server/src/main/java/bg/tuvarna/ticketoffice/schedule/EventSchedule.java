package bg.tuvarna.ticketoffice.schedule;

import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.repository.DistributorRepository;
import bg.tuvarna.ticketoffice.repository.EventRepository;
import bg.tuvarna.ticketoffice.service.NotificationService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventSchedule {

    private final EventRepository eventRepository;
    private final DistributorRepository distributorRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final NotificationService notificationService;

    @Autowired
    public EventSchedule(EventRepository eventRepository,
                         DistributorRepository distributorRepository,
                         ResourceBundleUtil resourceBundleUtil,
                         NotificationService notificationService) {
        this.eventRepository = eventRepository;
        this.distributorRepository = distributorRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.notificationService = notificationService;
    }

//    @Scheduled(cron = "${cron.sold-tickets}")
    public void soldEventTickets() {
        List<Event> events = eventRepository.findByStartDateAfter(LocalDateTime.now());

        for (Event event : events) {
            Long soldTickets = eventRepository.soldEventTickets(event.getId());
            String message = resourceBundleUtil.getMessage("notificationCreate.soldEventTickets",
                    soldTickets, event.getPlacesCount(), event.getType());

            List<Long> userIds = List.of(event.getUser().getId());
            notificationService.create(userIds, message);
        }
    }

//    @Scheduled(cron = "${cron.last-day-event}")
    public void lastDayOfEvent() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime startDate = localDateTime.with(LocalTime.MIN);
        LocalDateTime endDate = localDateTime.with(LocalTime.MAX);
        List<Event> events = eventRepository.findByStartDateBetween(startDate, endDate);

        for (Event event : events) {
            Long leftTickets = eventRepository.leftEventTickets(event.getId());
            if (leftTickets > 0) {
                String message = resourceBundleUtil
                        .getMessage("notificationCreate.eventLastDay", event.getType(), leftTickets);

                List<Long> usersId = distributorRepository.findById_Event_Id(event.getId())
                        .stream()
                        .map(d -> d.getId().getUser().getId())
                        .collect(Collectors.toList());

                usersId.add(event.getUser().getId());

                notificationService.create(usersId, message);
            }
        }
    }
}
