package bg.tuvarna.ticketoffice.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {

    @NotEmpty(message = "{ticketCreate.emptyCustomerUcn}")
    @Length(min = 10, max = 10, message = "{ticketCreate.invalidCustomerUcn}")
    @JsonProperty("customer_ucn")
    private String customerUcn;

    @NotEmpty(message = "{ticketCreate.emptyCustomerName}")
    @Length(min = 3, message = "{ticketCreate.invalidCustomerName}")
    @JsonProperty("customer_name")
    private String customerName;

    @NotNull(message = "{ticketCreate.nullTicketsCount}")
    @Positive(message = "{ticketCreate.invalidTicketsCount}")
    @JsonProperty("tickets_count")
    private Integer ticketsCount;

    @NotNull(message = "{ticketCreate.nullEventId}")
    @JsonProperty("event_id")
    private Long eventId;
}
