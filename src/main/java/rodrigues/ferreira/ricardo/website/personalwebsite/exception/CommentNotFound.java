package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class CommentNotFound extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public CommentNotFound(String reason) {
        super(reason);
    }

    public CommentNotFound(Long commentId) {
        super(String.format("Not Found Any Comment With %d id", commentId));
    }
}
