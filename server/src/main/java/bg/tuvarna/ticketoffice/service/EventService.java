package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EventService {

    public ResponseEntity<CommonMessageResponse> create(CreateEventRequest createEventRequest, User user);

    public ResponseEntity<CommonMessageResponse> edit(EditEventRequest eventRequest, User user);

    public ResponseEntity<EventDetailsResponse> details(Long eventId, User user);
}
