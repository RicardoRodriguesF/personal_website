package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordUpdateWithTokenRequest {
    @NotBlank
    private String password;

    @NotBlank
    private String token;
}
