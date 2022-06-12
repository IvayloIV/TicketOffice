package bg.tuvarna.ticketoffice.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude="distributors")
@EqualsAndHashCode(exclude="distributors")
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "id.event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distributor> distributors;
}
