package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class VoteException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public VoteException(String reason) {
        super(reason);
    }

    public VoteException(Long voteId) {
        this(String.format("Post not found with id : '%s", voteId));
    }
}
