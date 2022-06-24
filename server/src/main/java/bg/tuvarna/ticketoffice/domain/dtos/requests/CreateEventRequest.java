package bg.tuvarna.ticketoffice.domain.dtos.requests;

import bg.tuvarna.ticketoffice.domain.groups.Extended;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    @NotEmpty(message = "{eventCreate.emptyType}")
    @Length(min = 2, message = "{eventCreate.invalidType}", groups = Extended.class)
    private String type;

    @NotNull(message = "{eventCreate.nullPlacesCount}")
    @Min(value = 5, message = "{eventCreate.invalidPlacesCount}", groups = Extended.class)
    @JsonProperty("places_count")
    private Integer placesCount;

    @Length(min = 3, message = "{eventCreate.invalidPlacesType}")
    @JsonProperty("places_type")
    private String placesType;

    @NotNull(message = "{eventCreate.nullPrice}")
    @DecimalMin(value = "0.5", message = "{eventCreate.invalidPrice}", groups = Extended.class)
    private Double price;

    @Positive(message = "{eventCreate.invalidTicketsPerUser}")
    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @NotNull(message = "{eventCreate.nullStartDate}")
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @NotEmpty(message = "{eventCreate.emptyLocation}")
    @Length(min = 2, message = "{eventCreate.invalidLocation}", groups = Extended.class)
    private String location;

    @NotEmpty(message = "{eventCreate.emptyDistributorList}")
    @JsonProperty("distributor_ids")
    private List<Long> distributorIds;
}
