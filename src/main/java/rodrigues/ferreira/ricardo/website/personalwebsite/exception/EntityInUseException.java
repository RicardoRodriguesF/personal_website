package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class EntitInUseException extends ApiRequestException {
    public EntitInUseException(String message) {
        super(message);
    }

    public EntitInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
