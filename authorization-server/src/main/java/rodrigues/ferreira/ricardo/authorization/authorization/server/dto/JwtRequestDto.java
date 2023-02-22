/**
 * 
 */
package rodrigues.ferreira.ricardo.authorization.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The request DTO used for authentication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto {

	private String usernameOrEmail;
	private String password;

}
