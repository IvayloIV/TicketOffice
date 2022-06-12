package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateTicketRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    public ResponseEntity<CommonMessageResponse> create(CreateTicketRequest createTicketRequest, User user);
}
