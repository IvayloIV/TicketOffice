package bg.tuvarna.ticketoffice.service;

import bg.tuvarna.ticketoffice.domain.dtos.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.domain.entities.User;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.repository.RatingRepository;
import bg.tuvarna.ticketoffice.repository.UserRepository;
import bg.tuvarna.ticketoffice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingRepository ratingRepository;

    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, null, ratingRepository,
                null, null, null);
    }

    @Test
    public void testUserProfile_WithOrganiserInput_ExpectSuccessfulRetrieve() {
        UserProfileResponse organiserResponse = new UserProfileResponse();
        organiserResponse.setRole(Role.ORGANISER);
        organiserResponse.setName("Ivan");
        organiserResponse.setEventsCount(5L);
        organiserResponse.setActiveEvents(3L);
        Mockito.when(userRepository.getOrganiserProfile(Mockito.any())).thenReturn(organiserResponse);

        User user = new User();
        user.setId(2L);
        user.setName("Ivan");
        user.setRole(Role.ORGANISER);

        ResponseEntity<UserProfileResponse> profileResponse = userService.profile(user);
        HttpStatus statusCode = profileResponse.getStatusCode();
        UserProfileResponse userProfileResponse = profileResponse.getBody();

        Assertions.assertEquals(HttpStatus.OK, statusCode);
        Assertions.assertEquals(user.getName(), userProfileResponse.getName());
        Assertions.assertEquals(5L, userProfileResponse.getEventsCount());
        Assertions.assertEquals(3L, userProfileResponse.getActiveEvents());
    }

    @Test
    public void testUserProfile_WithDistributorInput_ExpectSuccessfulRetrieve() {
        UserProfileResponse distributorResponse = new UserProfileResponse();
        distributorResponse.setRole(Role.DISTRIBUTOR);
        distributorResponse.setName("Todor");
        distributorResponse.setEventsCount(2L);
        distributorResponse.setActiveEvents(1L);
        distributorResponse.setAverageRating(3.5);
        Mockito.when(userRepository.getDistributorProfile(Mockito.any())).thenReturn(distributorResponse);

        User user = new User();
        user.setId(3L);
        user.setName("Todor");
        user.setRole(Role.DISTRIBUTOR);

        ResponseEntity<UserProfileResponse> profileResponse = userService.profile(user);
        HttpStatus statusCode = profileResponse.getStatusCode();
        UserProfileResponse userProfileResponse = profileResponse.getBody();

        Assertions.assertEquals(HttpStatus.OK, statusCode);
        Assertions.assertEquals(user.getName(), userProfileResponse.getName());
        Assertions.assertEquals(2L, userProfileResponse.getEventsCount());
        Assertions.assertEquals(1L, userProfileResponse.getActiveEvents());
        Assertions.assertEquals(3.5, userProfileResponse.getAverageRating());
    }
}
