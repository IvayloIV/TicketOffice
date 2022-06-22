package bg.tuvarna.ticketoffice.services;

import java.util.List;
import java.util.Map;

import bg.tuvarna.ticketoffice.domain.models.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.DistributorListFilterRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface DistributorService {

    @GET("distributor/list")
    Call<List<DistributorListResponse>> list(@QueryMap Map<String, String> options, @Header("Authorization") String authorization);
}
