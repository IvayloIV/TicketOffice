package bg.tuvarna.ticketoffice.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @Column(name = "value", nullable = false)
    private Integer value;
}
