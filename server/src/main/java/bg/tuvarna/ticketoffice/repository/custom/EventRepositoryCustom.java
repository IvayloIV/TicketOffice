package bg.tuvarna.ticketoffice.repository.custom;

import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;

import java.util.List;

public interface EventRepositoryCustom {

    public List<EventListResponse> getOrganiserEvents(EventListFilterRequest filters, Long organiserId);

    public List<EventListResponse> getDistributorEvents(EventListFilterRequest filters, Long distributorId);
}
