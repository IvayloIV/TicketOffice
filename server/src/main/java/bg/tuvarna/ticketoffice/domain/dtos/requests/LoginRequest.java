package bg.tuvarna.ticketoffice.domain.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "{userLogin.blankName}")
    private String name;

    @NotBlank(message = "{userLogin.blankPassword}")
    private String password;
}
