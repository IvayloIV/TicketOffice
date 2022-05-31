package bg.tuvarna.ticketoffice.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_ucn", nullable = false, unique = true, length = 10)
    private String customerUcn;

    @Column(name = "customer_name", nullable = false, length = 127)
    private String customerName;

    @Column(name = "tickets_count", nullable = false)
    private Integer ticketsCount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;
}
