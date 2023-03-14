package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserIdOrderByVoteIdDesc(Post post, Long userId);
}
