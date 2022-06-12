package bg.tuvarna.ticketoffice.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {

    @JsonProperty("customer_ucn")
    private String customerUcn;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("tickets_count")
    private Integer ticketsCount;

    @JsonProperty("event_id")
    private Long eventId;
}
