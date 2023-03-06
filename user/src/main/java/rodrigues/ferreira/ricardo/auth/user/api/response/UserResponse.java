package rodrigues.ferreira.ricardo.auth.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private UserEntity.Type type;

    public static UserResponse of(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getType()
        );
    }
}
