package bg.tuvarna.ticketoffice.domain.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorListResponse {

    private Long id;

    private String name;

    private Double rating;

    private Long soldTickets;
}
