package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.PostMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.impl.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody @Valid PostDTO postInput) {
        Post post = postMapper.toEntity(postInput);
        post = postService.createPost(post);

        return postMapper.toDto(post);
    }

    @GetMapping("/getAll")
    public Page<PostDTO> getAllPost(@PageableDefault(size = 10) Pageable pageable) {
        Page<Post> postPage = postService.getPostPaged(pageable);
        List<PostDTO> postDTOList = postMapper.toCollectionDto(postPage.getContent());
        //todo falta implementar a ordenação, talvez eu queira por data ou por popularidade

        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable("id") Long posId) {
        Post post = postService.findOrFail(posId);
        return postMapper.toDto(post);
    }

    /* update post */
    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable("id") Long postId,
                              @RequestBody @Valid PostDTO postDTO ) {
        Post post = postService.findOrFail(postId);
        postMapper.copyToEntity(postDTO, post);
        post = postService.createPost(post);

        return postMapper.toDto(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
    }
}
