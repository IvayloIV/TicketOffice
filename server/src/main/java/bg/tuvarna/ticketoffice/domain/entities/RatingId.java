package bg.tuvarna.ticketoffice.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class RatingId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_from", referencedColumnName = "id", nullable = false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to", referencedColumnName = "id", nullable = false)
    private User userTo;
}
