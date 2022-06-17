package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.CreateTicketRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.entities.*;
import bg.tuvarna.ticketoffice.repository.DistributorRepository;
import bg.tuvarna.ticketoffice.repository.EventRepository;
import bg.tuvarna.ticketoffice.repository.TicketRepository;
import bg.tuvarna.ticketoffice.service.TicketService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final DistributorRepository distributorRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             EventRepository eventRepository,
                             DistributorRepository distributorRepository,
                             ResourceBundleUtil resourceBundleUtil,
                             ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.distributorRepository = distributorRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CommonMessageResponse> create(CreateTicketRequest createTicketRequest, User user) {
        Ticket ticket = modelMapper.map(createTicketRequest, Ticket.class);

        Event event = eventRepository.findById(createTicketRequest.getEventId())
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("ticketCreate.eventNotFound")));

        distributorRepository.findById_User_IdAndId_Event_Id(user.getId(), createTicketRequest.getEventId())
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("ticketCreate.invalidDistributor")));

        LocalDateTime dateTime = LocalDateTime.now();
        if (event.getStartDate().compareTo(dateTime) < 0) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("ticketCreate.eventStarted"));
        }

        List<Ticket> ticketsByCustomer = ticketRepository.findByCustomerUcnAndEventId(createTicketRequest.getCustomerUcn(), event.getId());
        int boughtTicket = ticketsByCustomer.stream().mapToInt(Ticket::getTicketsCount).sum();
        if (boughtTicket + createTicketRequest.getTicketsCount() > event.getTicketsPerUser()) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("ticketCreate.invalidTicketCount", event.getTicketsPerUser()));
        }

        Long leftTickets = eventRepository.leftEventTickets(event.getId());
        if (createTicketRequest.getTicketsCount() > leftTickets) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("ticketCreate.soldTicketsError", leftTickets));
        }

        ticket.setId(null);
        ticket.setBoughtDate(dateTime);
        ticket.setEvent(event);
        ticket.setUser(user);
        ticketRepository.save(ticket);

        String ticketCreatedMessage = resourceBundleUtil.getMessage("ticketCreate.successful");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonMessageResponse(ticketCreatedMessage));
    }
}
