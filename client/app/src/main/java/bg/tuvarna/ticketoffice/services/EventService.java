package bg.tuvarna.ticketoffice.services;

import java.util.List;

import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EventService {

    @GET("event/distributor")
    Call<List<EventListResponse>> distributorEvents(@Header("Authorization") String authorization);
}
