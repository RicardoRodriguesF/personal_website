package rodrigues.ferreira.ricardo.website.personalwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
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

    public List<Comment> getCommentsByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

   /* public Page<Comment> getCommentPaged(Pageable pageble) {
        return repository.findAll(pageble);
    }*/
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }

    public Comment findOrElseThrow(Long CommentId) {
        return repository.findById(CommentId).orElseThrow(() -> new CommentNotFound(CommentId));
    }


}
