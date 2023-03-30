package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface PostRepositoryQueries {
    List<Post> findTitleFilterDate(String title, Instant createdOn, Instant updatedOn);

    List<Post> findNewPostsVoteData();
}
