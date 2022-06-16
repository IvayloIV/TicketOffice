package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DistributorService {

    public ResponseEntity<List<DistributorListResponse>> list(DistributorListFilterRequest filters, User organiser);
}
