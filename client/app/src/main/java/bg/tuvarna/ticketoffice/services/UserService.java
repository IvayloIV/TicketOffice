package bg.tuvarna.ticketoffice.services;

import bg.tuvarna.ticketoffice.domain.models.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.RateUserRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.UserProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<CommonMessageResponse> register(@Body RegisterRequest registerRequest, @Header("Authorization") String authorization);

    @GET("user/profile")
    Call<UserProfileResponse> profile(@Header("Authorization") String authorization);

    @GET("user/details/{id}")
    Call<UserProfileResponse> details(@Path("id") Long userId, @Header("Authorization") String authorization);

    @POST("user/rate")
    Call<CommonMessageResponse> rate(@Body RateUserRequest rateUserRequest, @Header("Authorization") String authorization);
}
