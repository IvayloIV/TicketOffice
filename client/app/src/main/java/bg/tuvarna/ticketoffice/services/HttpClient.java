package bg.tuvarna.ticketoffice.services;

import java.lang.annotation.Annotation;

import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpClient {

    private static final String PROTOCOL = "http";
    private static final String IP = "192.168.0.101";
    private static final String PORT = "8080";

    private final Converter<ResponseBody, CommonMessageResponse> converter;
    private final UserService userService;

    private Role userRole;
    private String jwt;

    public HttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(String.format("%s://%s:%s/", PROTOCOL, IP, PORT))
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        converter = retrofit.responseBodyConverter(CommonMessageResponse.class, new Annotation[0]);

        userService = retrofit.create(UserService.class);
    }

    public Converter<ResponseBody, CommonMessageResponse> getConverter() {
        return converter;
    }

    public UserService getUserService() {
        return userService;
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
}