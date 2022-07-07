package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.config.OpenAPIConfig;
import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.groups.TicketValidationSequence;
import bg.tuvarna.ticketoffice.service.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
@Tag(name = "Event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CommonMessageResponse> create(@Validated(TicketValidationSequence.class) @RequestBody CreateEventRequest createEventRequest,
                                                        Authentication authentication) {
        return eventService.create(createEventRequest, (User) authentication.getPrincipal());
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<CommonMessageResponse> edit(@Validated(TicketValidationSequence.class) @RequestBody EditEventRequest editEventRequest,
                                                        Authentication authentication) {
        return eventService.edit(editEventRequest, (User) authentication.getPrincipal());
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<EventDetailsResponse> details(@PathVariable Long id, Authentication authentication) {
        return eventService.details(id, (User) authentication.getPrincipal());
    }

    @GetMapping(value = "/distributor")
    public ResponseEntity<List<EventListResponse>> getByDistributor(Authentication authentication) {
        return eventService.getByDistributor((User) authentication.getPrincipal());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<EventListResponse>> getList(EventListFilterRequest filterRequest,
                                                           Authentication authentication) {
        return eventService.list(filterRequest, (User) authentication.getPrincipal());
    }
}
