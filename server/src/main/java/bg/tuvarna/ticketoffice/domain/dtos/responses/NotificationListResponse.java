package bg.tuvarna.ticketoffice.domain.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationListResponse {

    private Long id;

    private String message;
}
