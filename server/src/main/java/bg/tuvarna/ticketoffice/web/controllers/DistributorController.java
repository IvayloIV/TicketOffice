package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.config.OpenAPIConfig;
import bg.tuvarna.ticketoffice.domain.dtos.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.service.DistributorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distributor")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
@Tag(name = "Distributor")
public class DistributorController {

    private final DistributorService distributorService;

    @Autowired
    public DistributorController(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<DistributorListResponse>> getList(DistributorListFilterRequest filterRequest,
                                                                 Authentication authentication) {
        return distributorService.list(filterRequest, (User) authentication.getPrincipal());
    }
}
