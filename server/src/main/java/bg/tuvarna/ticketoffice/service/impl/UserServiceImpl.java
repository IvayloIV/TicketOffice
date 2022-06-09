package bg.tuvarna.ticketoffice.service.impl;

import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.jwtutil.JwtTokenUtil;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.UserService;
import bg.tuvarna.ticketoffice.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ResourceBundleUtil resourceBundleUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Lazy AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil,
                           ResourceBundleUtil resourceBundleUtil,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.resourceBundleUtil = resourceBundleUtil;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<CommonMessageResponse> register(RegisterRequest registerRequest) {
        String roleStr = registerRequest.getRole().toUpperCase();
        Role role;

        try {
            role = Role.valueOf(roleStr);
            if (role.equals(Role.ADMIN)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("userRegister.invalidRole"));
        }

        if (userRepository.existsByNameIgnoreCase(registerRequest.getName())) {
            throw new IllegalArgumentException(resourceBundleUtil.getMessage("userRegister.nameAlreadyExists"));
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        String successfulMessage = resourceBundleUtil.getMessage("userRegister.successful");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonMessageResponse(successfulMessage));
    }

    @Override
    public ResponseEntity<UserProfileResponse> profile(User user) {
        Function<Long, UserProfileResponse> getUserProfile;

        if (user.getRole().equals(Role.ORGANISER)) {
            getUserProfile = userRepository::getOrganiserProfile;
        } else {
            getUserProfile = userRepository::getDistributorProfile;
        }

        return ResponseEntity.ok(getUserProfile.apply(user.getId()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
