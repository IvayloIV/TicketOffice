package bg.tuvarna.ticketoffice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.annotation.Annotation;

import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpClient {

    private static final String PROTOCOL = "http";
    private static final String IP = "192.168.0.102";
    private static final String PORT = "8080";
    private static HttpClient client;

    private final Converter<ResponseBody, CommonMessageResponse> converter;
    private final UserService userService;
    private final NotificationService notificationService;
    private final EventService eventService;
    private final TicketService ticketService;

    private Role userRole;
    private String jwt;

    private HttpClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(String.format("%s://%s:%s/", PROTOCOL, IP, PORT))
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();

        converter = retrofit.responseBodyConverter(CommonMessageResponse.class, new Annotation[0]);

        userService = retrofit.create(UserService.class);
        notificationService = retrofit.create(NotificationService.class);
        eventService = retrofit.create(EventService.class);
        ticketService = retrofit.create(TicketService.class);
    }

    public static synchronized HttpClient getInstance() {
        if (client == null) {
            client = new HttpClient();
        }

        return client;
    }

    public Converter<ResponseBody, CommonMessageResponse> getConverter() {
        return converter;
    }

    public UserService getUserService() {
        return userService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getAuthorizationHeader() {
        return "Bearer " + jwt;
    }
}
