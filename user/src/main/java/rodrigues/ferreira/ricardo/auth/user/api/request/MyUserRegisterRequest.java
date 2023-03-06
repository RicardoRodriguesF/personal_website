package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MyUserRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public UserEntity toEntity() {
        return new UserEntity(
                this.name,
                this.email,
                this.password,
                UserEntity.Type.CLIENT
        );
    }
}
