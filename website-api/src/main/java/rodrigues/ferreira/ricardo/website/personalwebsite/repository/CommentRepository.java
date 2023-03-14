package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment> {
    List<Comment> findByPost(Post post);

}
