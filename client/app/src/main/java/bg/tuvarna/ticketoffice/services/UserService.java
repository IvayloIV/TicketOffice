package bg.tuvarna.ticketoffice.services;

import bg.tuvarna.ticketoffice.domain.models.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> create(@Body LoginRequest loginRequest);
}
