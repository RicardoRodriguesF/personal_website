package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
