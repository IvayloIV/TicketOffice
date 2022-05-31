package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
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
}
