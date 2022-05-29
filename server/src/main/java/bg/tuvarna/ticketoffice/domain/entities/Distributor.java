package bg.tuvarna.ticketoffice.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "distributor")
public class Distributor {

    @EmbeddedId
    private DistributorId id;
}
