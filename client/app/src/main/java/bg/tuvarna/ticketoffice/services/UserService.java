package bg.tuvarna.ticketoffice.services;

import bg.tuvarna.ticketoffice.domain.models.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<CommonMessageResponse> register(@Body RegisterRequest registerRequest, @Header("Authorization") String authorization);
}
