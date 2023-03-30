package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CommentDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.PostNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.CommentMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.SecurityService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.CommentService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post/{postId}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserReactiveClient userReactiveClient;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/new_comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@PathVariable(value = "postId") Long postId,
                                    @RequestBody @Valid CommentDTO commentDTO) {
        Post post = postService.findPostOrElseThrow(postId);
        Comment comment = commentMapper.convertToEntity(commentDTO);
        comment.setPost(post);
        comment = commentService.createComment(comment);

        return commentMapper.convertToDto(comment);
    }

    @GetMapping("/comments")
    public @ResponseBody List<CommentDTO> showAllCommentsForPost(@PathVariable("postId") Long postId) {
        Post post = postService.findPostOrElseThrow(postId);
        if (!postId.equals(post.getPostId())) {
            throw new PostNotFoundException(postId);
        }
        List<Comment> commentList = commentService.getCommentsByPostId(post);
        return commentMapper.toCollectionDto(commentList);
    }

    @GetMapping("/comment/{userName}")
    public @ResponseBody List<CommentDTO> showAllCommentsForUser(@RequestParam String userName) {
        UserResponse user = securityService.getCurrentUser();
        List<Comment> commentList = commentService.getCommentsByUserId(user.getName());
        return commentMapper.toCollectionDto(commentList);
    }

    @GetMapping("/comment/{id}")
    public CommentDTO showSingleComment(@PathVariable("postId") Long postId,
                                        @PathVariable("id") Long commentId) {
        Post post = postService.findPostOrElseThrow(postId);
        Comment comment = commentService.findCommentOrElseThrow(commentId);

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new PostNotFoundException(postId);
        }
        return commentMapper.convertToDto(comment);
    }

    /* update comment */
    @PutMapping("/comment/edit/{id}")
    public CommentDTO updateComment(@PathVariable("postId") Long postId,
                                    @PathVariable("id") Long commentId,
                              @RequestBody @Valid CommentDTO commentDTO ) {
        Post post = postService.findPostOrElseThrow(postId);
        Comment comment = commentService.findCommentOrElseThrow(commentId);

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new PostNotFoundException(postId);
        }

        commentMapper.copyToDomainObject(commentDTO, comment);

        return commentMapper.convertToDto(commentService.createComment(comment));
    }

    @DeleteMapping("/comment/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("postId") Long postId,
                              @PathVariable("id") Long commentId) {
        Post post = postService.findPostOrElseThrow(postId);
        Comment comment = commentService.findCommentOrElseThrow(commentId);

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new PostNotFoundException(postId);
        }
        commentService.deleteComment(comment.getId());
    }
}
