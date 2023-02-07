package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public PostNotFoundException(String reason) {
        super(reason);
    }

    public PostNotFoundException(Long postId) {
        this(String.format("Post not found with id : '%s", postId));

    }
}
