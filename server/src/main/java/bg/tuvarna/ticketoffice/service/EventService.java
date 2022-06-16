package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.requests.*;
import bg.tuvarna.ticketoffice.domain.dtos.responses.*;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EventService {

    public ResponseEntity<CommonMessageResponse> create(CreateEventRequest createEventRequest, User user);

    public ResponseEntity<CommonMessageResponse> edit(EditEventRequest eventRequest, User user);

    public ResponseEntity<EventDetailsResponse> details(Long eventId, User user);

    public ResponseEntity<List<EventListResponse>> getByDistributor(User user);

    public ResponseEntity<List<EventListResponse>> list(EventListFilterRequest filterRequest, User user);
}
