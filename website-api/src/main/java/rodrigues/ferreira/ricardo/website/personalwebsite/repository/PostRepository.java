package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.StatusPost;

import java.util.List;

@Repository
public interface PostRepository extends CustomJpaRepository<Post, Long>, PostRepositoryQueries,
        JpaSpecificationExecutor<Post> {

    List<Post> findByAuthorName(String authorName);
  /*  @Query("FROM posts p WHERE p.status_post = :status and p.author_name = :authorName" )
    List<Post> findOut(@Param("status") StatusPost status, @Param("authorName") String authorName);
*/
    List<Post> findByStatusPostAndAuthorName(StatusPost status, String authorName);
}
