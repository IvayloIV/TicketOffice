package bg.tuvarna.ticketoffice.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    private String type;

    @JsonProperty("places_count")
    private Integer placesCount;

    @JsonProperty("places_type")
    private String placesType;

    private Double price;

    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    private String location;

    @JsonProperty("distributor_ids")
    private List<Long> distributorIds;
}
