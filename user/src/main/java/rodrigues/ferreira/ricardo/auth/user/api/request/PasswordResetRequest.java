package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequest {
    @Email
    @NotBlank
    private String email;
}
