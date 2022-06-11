package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.entities.Distributor;
import bg.tuvarna.ticketoffice.domain.entities.DistributorId;
import bg.tuvarna.ticketoffice.domain.entities.Event;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.repository.DistributorRepository;
import bg.tuvarna.ticketoffice.repository.EventRepository;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.EventService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            UserRepository userRepository,
                            ResourceBundleUtil resourceBundleUtil,
                            ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CommonMessageResponse> create(CreateEventRequest createEventRequest, User user) {
        if (eventRepository.existsByType(createEventRequest.getType())) {
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
        return ResponseEntity.ok(new CommonMessageResponse(eventCreatedMessage));
    }
}
