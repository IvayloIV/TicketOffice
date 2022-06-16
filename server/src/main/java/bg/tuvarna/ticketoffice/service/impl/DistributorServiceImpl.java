package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.repository.DistributorRepository;
import bg.tuvarna.ticketoffice.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorServiceImpl implements DistributorService {

    private final DistributorRepository distributorRepository;

    @Autowired
    public DistributorServiceImpl(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    @Override
    public ResponseEntity<List<DistributorListResponse>> list(DistributorListFilterRequest filters, User organiser) {
        return ResponseEntity.ok(distributorRepository.getFilteredDistributors(filters, organiser.getId()));
    }
}
