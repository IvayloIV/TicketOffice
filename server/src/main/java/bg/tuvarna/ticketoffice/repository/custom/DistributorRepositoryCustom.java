package bg.tuvarna.ticketoffice.repository.custom;

import bg.tuvarna.ticketoffice.domain.dtos.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.EventListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.EventListResponse;

import java.util.List;

public interface DistributorRepositoryCustom {

    public List<DistributorListResponse> getFilteredDistributors(DistributorListFilterRequest filter, Long organiserId);
}
