package bg.tuvarna.ticketoffice.domain.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventListResponse {

    private Long id;

    private String type;

    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    private String location;
}
