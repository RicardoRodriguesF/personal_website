package rodrigues.ferreira.ricardo.website.personalwebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.StatusPost;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.PostNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.PostRepository;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public Post createPost(Post post) {
        if (post.getStatusPost().getDescription().equals("published")) {
            post.published();
        }
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getPostsPaged(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findPostOrElseThrow(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.flush();
        return post;
    }

    public List<Post> findByAuthorName(String authorName) {
        List<Post> postList = postRepository.findByAuthorName(authorName);
        if (postList == null) {
            throw new PostNotFoundException(authorName);
        }
        return postList;
    }

    public List<Post> findMyPosts(String authorName) {
        List<Post> postList = postRepository.findByAuthorName(authorName);
        if (postList == null) {
            throw new PostNotFoundException(authorName);
        }
        return postList;
    }

    public List<Post> findPostsPublishedByAuthor(StatusPost status, String authorName) {
        List<Post> postList = postRepository.findByStatusPostAndAuthorName(status, authorName);
        if (postList == null) {
            throw new PostNotFoundException(authorName);
        }
        return postList;
    }
    public List<Post> findTitleFilterDate(String title, Instant createdOn, Instant updatedOn) {
        return postRepository.findTitleFilterDate(title, createdOn, updatedOn);
    }


    public void deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            postRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(id);
        }
    }

    public List<Post> findNewerPostsVoted() {
        return postRepository.findNewPostsVoteData();
    }

    public Post findOnePost() {
        return postRepository.getRandom().orElse(null);
    }
}
