package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ResourceNotFoundException(String reason){
        this(HttpStatus.NOT_FOUND, reason);
    }
}
