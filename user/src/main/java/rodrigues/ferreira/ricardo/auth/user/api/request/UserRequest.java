package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Getter;
import lombok.Setter;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private UserEntity.Type type;

    public UserEntity toEntity() {
        return new UserEntity(
                this.name,
                this.email,
                this.password,
                this.type
        );
    }
}
