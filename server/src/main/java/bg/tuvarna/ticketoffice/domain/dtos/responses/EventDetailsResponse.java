package bg.tuvarna.ticketoffice.domain.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailsResponse {

    private Long id;

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
