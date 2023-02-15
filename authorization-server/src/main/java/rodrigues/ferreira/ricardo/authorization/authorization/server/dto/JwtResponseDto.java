/**
 * 
 */
package rodrigues.ferreira.ricardo.authorization.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * The response DTO used for authentication.
 */
@Getter
@AllArgsConstructor
public class JwtResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String token;
	
	private Long id;
	
	private String username;
}
