package bg.tuvarna.ticketoffice.services;

import java.util.List;

import bg.tuvarna.ticketoffice.domain.models.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.CreateTicketRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {

    @GET("event/distributor")
    Call<List<EventListResponse>> distributorEvents(@Header("Authorization") String authorization);

    @GET("event/details/{id}")
    Call<EventDetailsResponse> details(@Path("id") Long eventId, @Header("Authorization") String authorization);

    @POST("event/create")
    Call<CommonMessageResponse> create(@Body CreateEventRequest createEventRequest, @Header("Authorization") String authorization);

    @PUT("event/edit")
    Call<CommonMessageResponse> edit(@Body EditEventRequest editEventRequest, @Header("Authorization") String authorization);
}
