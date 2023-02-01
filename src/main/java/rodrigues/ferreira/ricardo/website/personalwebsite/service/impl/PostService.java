package rodrigues.ferreira.ricardo.website.personalwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.PostNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getPostPaged(Pageable pageble) {
        return postRepository.findAll(pageble);
    }

    public Post findOrFail(Long posId) {
        return postRepository.findById(posId).orElseThrow(() -> new PostNotFoundException(posId));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
