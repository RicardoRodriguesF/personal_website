package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.AuthorResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.PostRequest;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.output.PostResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostShortDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.MapperPost;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.PostMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.CanWritePosts;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.SecurityService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.CategoryService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private MapperPost mapperPost;

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
    public PostResponse createPost(@RequestBody @Valid PostRequest postRequest) {
        Category category = categoryService.findOrElseThrow(postRequest.getCategoryId());
        Post post = mapperPost.map(postRequest, category, securityService.getCurrentUser());
       /* post.setCategory(category);
        post.setAuthorId(securityService.getUserId());*/
        postService.createPost(post);
        return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> postMapper.convertToDtoWithAuthor
                        (post, AuthorResponse.of(userResponse)))
                .blockOptional()
                .orElseGet(() -> postMapper.convertToDto(post));
    }

    @GetMapping("/showAll")
    public Page<PostResponse> showAllPosts(@PageableDefault(size = 10) Pageable pageable) {
        Page<Post> postPage = postService.getPostsPaged(pageable);
        List<PostResponse> postDTOList = postMapper.toCollectionDto(postPage.getContent());
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
    public PostResponse showSinglePost(@PathVariable("id") Long postId) {
        Post post = postService.findPostOrElseThrow(postId);
       /* UserResponse user = userReactiveClient.findById(post.getAuthorId()).block();*/

        return mapperPost.mapToDto(postService.findPostOrElseThrow(postId));

        /*return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> mapperPost.mapToDto(post))
                .blockOptional()
                .orElseGet(() -> postMapper.convertToDto(post));*/

      /*  return userReactiveClient.findById(post.getAuthorId())
                .map(userResponse -> PostDetailedResponse.of(post, AuthorResponse.of(userResponse)))
                .blockOptional()
                .orElseGet(() -> PostDetailedResponse.of(post));
        return userClient.findById(post.getAuthorId())
                .map(userResponse -> PostDetailedResponse.of(post, AuthorResponse.of(userResponse)))
                .orElseGet(() -> PostDetailedResponse.of(post));*/
    }

    /* update post */
    @CanWritePosts
    @PutMapping("edit/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PostResponse updatePost(@PathVariable("id") Long postId,
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
