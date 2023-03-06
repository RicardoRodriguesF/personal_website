package rodrigues.ferreira.ricardo.auth.user.api.request;

import lombok.Getter;
import lombok.Setter;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private UserEntity.Type type;

    public void update(UserEntity currentUser) {
        currentUser.setEmail(this.email);
        currentUser.setName(this.name);
        currentUser.setType(this.type);
    }
}
