package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
}
