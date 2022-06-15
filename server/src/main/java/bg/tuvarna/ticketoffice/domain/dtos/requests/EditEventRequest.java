package bg.tuvarna.ticketoffice.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditEventRequest {

    @NotNull(message = "{eventEdit.nullId}")
    private Long id;

    @NotEmpty(message = "{eventEdit.emptyType}")
    @Length(min = 2, message = "{eventEdit.invalidType}")
    private String type;

    @NotNull(message = "{eventEdit.nullPlacesCount}")
    @Min(value = 5, message = "{eventEdit.invalidPlacesCount}")
    @JsonProperty("places_count")
    private Integer placesCount;

    @Length(min = 3, message = "{eventEdit.invalidPlacesType}")
    @JsonProperty("places_type")
    private String placesType;

    @NotNull(message = "{eventEdit.nullPrice}")
    @DecimalMin(value = "0.5", message = "{eventEdit.invalidPrice}")
    private Double price;

    @Positive(message = "{eventEdit.invalidTicketsPerUser}")
    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @NotNull(message = "{eventEdit.nullStartDate}")
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @NotEmpty(message = "{eventEdit.emptyLocation}")
    @Length(min = 2, message = "{eventEdit.invalidLocation}")
    private String location;

    @NotEmpty(message = "{eventEdit.emptyDistributorList}")
    @JsonProperty("distributor_ids")
    private List<Long> distributorIds;
}
