package bg.tuvarna.ticketoffice.services;

import bg.tuvarna.ticketoffice.domain.models.requests.CreateTicketRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TicketService {

    @POST("ticket/create")
    Call<CommonMessageResponse> create(@Body CreateTicketRequest createTicketRequest, @Header("Authorization") String authorization);
}
