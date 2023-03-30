package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.util.List;

public interface CommentRepository extends CustomJpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUsername(String username);
}
