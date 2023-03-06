package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class MyUserUpdatePasswordRequest {

    @NotBlank
    private String password;

}
