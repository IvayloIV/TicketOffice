package bg.tuvarna.ticketoffice.domain.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "{userRegister.blankName}")
    private String name;

    @NotBlank(message = "{userRegister.blankPassword}")
    private String password;

    @NotBlank(message = "{userRegister.blankRole}")
    private String role;
}
