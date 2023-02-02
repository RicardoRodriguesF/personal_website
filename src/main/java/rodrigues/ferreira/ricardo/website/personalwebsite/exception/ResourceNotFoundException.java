package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.FOUND)
public abstract class ResourceNotFoundException extends ApiRequestException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
