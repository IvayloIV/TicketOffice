package bg.tuvarna.ticketoffice.domain.entities;

import bg.tuvarna.ticketoffice.domain.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type", nullable = false, unique = true, length = 511)
    private String type;

    @Column(name = "places_count", nullable = false)
    private Integer placesCount;

    @Column(name = "places_type")
    private String placesType;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "tickets_per_user")
    private Integer ticketsPerUser;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
