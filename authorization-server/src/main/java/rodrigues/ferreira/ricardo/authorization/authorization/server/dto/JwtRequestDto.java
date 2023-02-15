/**
 * 
 */
package rodrigues.ferreira.ricardo.authorization.authorization.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * The request DTO used for authentication.
 */
@Data
public class JwtRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usernameOrEmail;
	private String password;

}
