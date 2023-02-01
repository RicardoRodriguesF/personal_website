package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class CommentNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommentNotFound(Long commentId) {
        super(String.format("Not Found Any Post With This %d id", commentId));
    }
}
