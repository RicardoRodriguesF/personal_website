package rodrigues.ferreira.ricardo.authorization.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String name;
    private String username;
    private String email;
    private String password;
}
