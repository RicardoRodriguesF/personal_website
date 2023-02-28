package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.AuthorResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostShortDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.PostRequest;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.PostMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.CanWritePosts;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.SecurityService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.impl.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserReactiveClient userReactiveClient;

    @Autowired
    private SecurityService securityService;

    @CanWritePosts
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody @Valid PostRequest postRequest) {
        Category category = categoryService.findOrElseThrow(postRequest.getCategoryId());
        Post post = postMapper.convertToEntity(postRequest);
        post.setCategory(category);
        post.setAuthorId(securityService.getUserId());
        postService.createPost(post);
        return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> postMapper.convertToDtoWithAuthor
                        (post, AuthorResponse.of(userResponse)))
                .blockOptional()
                .orElseGet(() -> postMapper.convertToDto(post));
    }

    @GetMapping("/showAll")
    public Page<PostDTO> showAllPosts(@PageableDefault(size = 10) Pageable pageable) {
        Page<Post> postPage = postService.getPostsPaged(pageable);
        List<PostDTO> postDTOList = postMapper.toCollectionDto(postPage.getContent());
        //todo falta implementar a ordenação, talvez eu queira por data ou por popularidade

        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    @GetMapping("/showAllShort")
    public Page<PostShortDTO> showAllShortPosts(@PageableDefault(size = 20) Pageable pageable) {
        Page<Post> postPage = postService.getPostsPaged(pageable);
        List<PostShortDTO> postDTOList = postMapper.toCollectionShortDto(postPage.getContent());
        //todo falta implementar a ordenação, talvez eu queira por data ou por popularidade

        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    @GetMapping("show/{id}")
    public PostDTO showSinglePost(@PathVariable("id") Long posId) {
        Post post = postService.findPostOrElseThrow(posId);

        return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> postMapper.convertToDtoWithAuthor
                        (post, AuthorResponse.of(userResponse)))
                .blockOptional()
                .orElseGet(() -> postMapper.convertToDto(post));
    }

    /* update post */
    @CanWritePosts
    @PutMapping("edit/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PostDTO updatePost(@PathVariable("id") Long postId,
                              @RequestBody @Valid PostRequest postRequest) {
        Post post = postService.findPostOrElseThrow(postId);
      /*  if (post.getAuthorId().equals(securityService.getUserId())) {
        }*/

        postMapper.copyToDomainObject(postRequest, post);

        return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> postMapper.convertToDtoWithAuthor
                        (post, AuthorResponse.of(userResponse)))
                .blockOptional()
                .orElseGet(() -> postMapper.convertToDto(post));
    }

    @CanWritePosts
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
    }
}
