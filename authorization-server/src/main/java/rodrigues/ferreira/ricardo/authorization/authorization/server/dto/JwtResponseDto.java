/**
 * 
 */
package rodrigues.ferreira.ricardo.authorization.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * The response DTO used for authentication.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String accessToken;
	private String refreshToken;
}
