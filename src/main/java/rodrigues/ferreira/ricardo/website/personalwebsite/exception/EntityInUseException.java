package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class EntityInUseException extends ApiRequestException {
    public EntityInUseException(String message) {
        super(message);
    }
}
