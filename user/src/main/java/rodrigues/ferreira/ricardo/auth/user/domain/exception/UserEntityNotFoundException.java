package rodrigues.ferreira.ricardo.auth.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserEntityNotFoundException extends RuntimeException {

	public UserEntityNotFoundException() {
		super("User not found.");
	}

	public UserEntityNotFoundException(String message) {
		super(message);
	}
}
