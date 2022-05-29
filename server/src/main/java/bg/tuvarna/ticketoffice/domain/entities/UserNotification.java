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
@Table(name = "user_notifications")
public class UserNotification {

    @EmbeddedId
    private UserNotificationId id;

    @Column(name = "seen", nullable = false)
    private Boolean seen;
}
