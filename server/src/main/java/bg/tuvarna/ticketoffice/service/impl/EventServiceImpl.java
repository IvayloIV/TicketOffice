package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;
import bg.tuvarna.ticketoffice.domain.entities.Distributor;
import bg.tuvarna.ticketoffice.domain.entities.DistributorId;
import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.repository.DistributorRepository;
import bg.tuvarna.ticketoffice.repository.EventRepository;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.EventService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final DistributorRepository distributorRepository;
    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            DistributorRepository distributorRepository,
                            UserRepository userRepository,
                            ResourceBundleUtil resourceBundleUtil,
                            ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.distributorRepository = distributorRepository;
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CommonMessageResponse> create(CreateEventRequest createEventRequest, User user) {
        if (eventRepository.countByType(createEventRequest.getType()) > 0) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("eventCreate.typeAlreadyExists"));
        }

        Event event = modelMapper.map(createEventRequest, Event.class);
        List<Distributor> distributors = createEventRequest.getDistributorIds().stream()
            .map(d -> {
                Optional<User> userOptional = userRepository.findById(d);
                User distributorUser = userOptional.orElseThrow(() ->
                        new IllegalArgumentException(resourceBundleUtil.getMessage("eventCreate.distributorNotFound", d)));

                DistributorId distributorId = new DistributorId(distributorUser, event);
                return new Distributor(distributorId);
            })
            .collect(Collectors.toList());

        event.setDistributors(distributors);
        event.setUser(user);
        eventRepository.save(event);

        String eventCreatedMessage = resourceBundleUtil.getMessage("eventCreate.successful");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonMessageResponse(eventCreatedMessage));
    }

    @Override
    public ResponseEntity<CommonMessageResponse> edit(EditEventRequest eventRequest, User user) {
        Event savedEvent = eventRepository.findById(eventRequest.getId())
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("eventEdit.notFound")));

        if (!savedEvent.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("eventEdit.invalidUser"));
        }

        if (!savedEvent.getType().equals(eventRequest.getType())) {
            if (eventRepository.countByType(eventRequest.getType()) > 0) {
                throw new IllegalArgumentException(resourceBundleUtil.getMessage("eventEdit.typeAlreadyExists"));
            }
            savedEvent.setType(eventRequest.getType());
        }

        savedEvent.getDistributors().clear();
        eventRequest.getDistributorIds()
            .forEach(d -> {
                Optional<User> userOptional = userRepository.findById(d);
                User distributorUser = userOptional.orElseThrow(() ->
                        new IllegalArgumentException(resourceBundleUtil.getMessage("eventEdit.distributorNotFound", d)));

                DistributorId distributorId = new DistributorId(distributorUser, savedEvent);
                savedEvent.getDistributors().add(new Distributor(distributorId));
            });

        savedEvent.setPlacesCount(eventRequest.getPlacesCount());
        savedEvent.setPlacesType(eventRequest.getPlacesType());
        savedEvent.setPrice(eventRequest.getPrice());
        savedEvent.setTicketsPerUser(eventRequest.getTicketsPerUser());
        savedEvent.setStartDate(eventRequest.getStartDate());
        savedEvent.setLocation(eventRequest.getLocation());
        eventRepository.save(savedEvent);

        String eventUpdatedMessage = resourceBundleUtil.getMessage("eventEdit.successful");
        return ResponseEntity.ok(new CommonMessageResponse(eventUpdatedMessage));
    }

    @Override
    public ResponseEntity<EventDetailsResponse> details(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("eventDetails.notFound")));

        if (!event.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("eventDetails.invalidUser"));
        }

        List<Long> distributorIds = event.getDistributors()
            .stream()
            .map(e -> e.getId().getUser().getId())
            .collect(Collectors.toList());

        EventDetailsResponse eventDetails = modelMapper.map(event, EventDetailsResponse.class);
        eventDetails.setDistributorIds(distributorIds);
        return ResponseEntity.ok(eventDetails);
    }

    @Override
    public ResponseEntity<List<EventListResponse>> getByDistributor(User user) {
        List<EventListResponse> eventsResponse = distributorRepository.findById_User_Id(user.getId())
            .stream()
            .map(d -> modelMapper.map(d.getId().getEvent(), EventListResponse.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(eventsResponse);
    }
}
