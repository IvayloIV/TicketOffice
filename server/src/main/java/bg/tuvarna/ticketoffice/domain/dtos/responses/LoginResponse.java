package bg.tuvarna.ticketoffice.domain.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;

    private String name;

    private String jwtToken;
}
