package bg.tuvarna.ticketoffice.web.controllers;

import bg.tuvarna.ticketoffice.domain.dtos.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.dtos.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private final TestRestTemplate testRestTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @LocalServerPort
    private int port;
    private static String baseUrl;

    @Autowired
    public UserControllerTest(TestRestTemplate testRestTemplate,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.testRestTemplate = testRestTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void init() {
        baseUrl = String.format("http://localhost:%s/user", port);
    }

    @Test
    public void testUserLogin_WithValidInput_ExpectSuccessfulLogin() {
        String username = "Kiril";
        String randomPassword = UUID.randomUUID().toString();

        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(randomPassword));
        user.setRole(Role.ORGANISER);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(username);
        loginRequest.setPassword(randomPassword);
        ResponseEntity<LoginResponse> loginResponseEntity = testRestTemplate
                .postForEntity(baseUrl + "/login", loginRequest, LoginResponse.class);
        LoginResponse loginResponse = loginResponseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, loginResponseEntity.getStatusCode());
        Assertions.assertEquals(username, loginResponse.getName());
        Assertions.assertEquals(Role.ORGANISER, loginResponse.getRole());
        Assertions.assertNotNull(loginResponse.getJwtToken());
    }
}
