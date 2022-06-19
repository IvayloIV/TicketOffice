package bg.tuvarna.ticketoffice.services;

import java.util.List;

import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NotificationService {

    @GET("notification/count")
    Call<Long> notificationCount(@Header("Authorization") String authorization);

    @GET("notification/list")
    Call<List<NotificationListResponse>> notificationList(@Header("Authorization") String authorization);
}
