package bg.tuvarna.ticketoffice.domain.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorListFilterRequest {

    private Long soldTickets;

    private Double rating;

    private String name;
}
