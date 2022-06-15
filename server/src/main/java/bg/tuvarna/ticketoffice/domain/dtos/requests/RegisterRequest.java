package bg.tuvarna.ticketoffice.domain.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "{userRegister.emptyName}")
    @Length(min = 3, message = "{userRegister.invalidName}")
    private String name;

    @NotEmpty(message = "{userRegister.emptyPassword}")
    @Length(min = 3, message = "{userRegister.invalidPassword}")
    private String password;

    @NotBlank(message = "{userRegister.blankRole}")
    private String role;
}
