package bg.tuvarna.ticketoffice.domain.entities;

import bg.tuvarna.ticketoffice.domain.dtos.responses.DistributorListResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "distributor")
@SqlResultSetMapping(name = "DistributorListMapping",
    classes = @ConstructorResult(
        targetClass = DistributorListResponse.class,
        columns = {@ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "rating", type = Double.class),
                @ColumnResult(name = "soldTickets", type = Long.class)}
    )
)
public class Distributor {

    @EmbeddedId
    private DistributorId id;
}
