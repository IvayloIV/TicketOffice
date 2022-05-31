package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.jwtutil.JwtTokenUtil;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Lazy AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        String name = loginRequest.getName();
        String password = loginRequest.getPassword();

        var authenticationToken = new UsernamePasswordAuthenticationToken(name, password);
        authenticationManager.authenticate(authenticationToken);

        User user = (User) loadUserByUsername(name);
        String token = jwtTokenUtil.generateToken(user);

        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getName(), token);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
