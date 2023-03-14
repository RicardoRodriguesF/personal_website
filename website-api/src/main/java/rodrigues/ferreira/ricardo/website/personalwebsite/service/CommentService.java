package rodrigues.ferreira.ricardo.website.personalwebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.CommentNotFound;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;


    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Post post) {
        return repository.findByPost(post);
    }

   /* public Page<Comment> getCommentPaged(Pageable pageble) {
        return repository.findAll(pageble);
    }*/
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }

    public Comment findCommentOrElseThrow(Long CommentId) {
        return repository.findById(CommentId).orElseThrow(() -> new CommentNotFound(CommentId));
    }


}
