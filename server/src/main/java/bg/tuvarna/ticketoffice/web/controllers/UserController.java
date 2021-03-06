package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.config.OpenAPIConfig;
import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.RateUserRequest;
import bg.tuvarna.ticketoffice.domain.dtos.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.groups.TicketValidationSequence;
import bg.tuvarna.ticketoffice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping(value = "/register")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<CommonMessageResponse> register(@Validated(TicketValidationSequence.class) @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @GetMapping(value = "/profile")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<UserProfileResponse> profile(Authentication authentication) {
        return userService.profile((User) authentication.getPrincipal());
    }

    @GetMapping(value = "/details/{id}")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<UserProfileResponse> details(@PathVariable Long id) {
        return userService.details(id);
    }

    @PostMapping(value = "/rate")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<CommonMessageResponse> rate(@RequestBody RateUserRequest rateUserRequest, Authentication authentication) {
        return userService.rate(rateUserRequest, (User) authentication.getPrincipal());
    }
}
