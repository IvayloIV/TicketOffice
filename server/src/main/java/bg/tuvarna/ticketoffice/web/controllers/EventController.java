package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CommonMessageResponse> create(@Valid @RequestBody CreateEventRequest createEventRequest,
                                                        Authentication authentication) {
        return eventService.create(createEventRequest, (User) authentication.getPrincipal());
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<CommonMessageResponse> edit(@Valid @RequestBody EditEventRequest editEventRequest,
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
