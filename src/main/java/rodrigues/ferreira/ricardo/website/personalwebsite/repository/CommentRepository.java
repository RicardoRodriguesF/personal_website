package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends BaseRepository<Comment> {
    List<Comment> findByPostId(Long postId);
}
