package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CommentDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.PostNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.CommentMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.impl.CommentService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.impl.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;
    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/{postId}/new_comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@PathVariable(value = "postId") Long postId,
                                    @RequestBody @Valid CommentDTO commentDTO) {
        Post post = postService.findOrElseThrow(postId);
        Comment comment = commentMapper.convertToEntity(commentDTO);
        comment.setPost(post);
        comment = commentService.createComment(comment);

        return commentMapper.convertToDto(comment);
    }

    @GetMapping("{postId}/comments")
    public @ResponseBody List<CommentDTO> getAllComment(@PathVariable("postId") Long postId) {
        Post post = postService.findOrElseThrow(postId);
        if (!postId.equals(post.getId())) {
            throw new PostNotFoundException(postId);
        }
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        return commentMapper.toCollectionDto(commentList);
    }

    @GetMapping("/{postId}/comment/{id}")
    public CommentDTO getCommentById(@PathVariable("postId") Long postId,
                                     @PathVariable("id") Long commentId) {
        Post post = postService.findOrElseThrow(postId);
        Comment comment = commentService.findOrElseThrow(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new PostNotFoundException(postId);
        }
        return commentMapper.convertToDto(comment);
    }

    /* update comment */
    @PutMapping("/{postId}/comment/{id}")
    public CommentDTO updateComment(@PathVariable("postId") Long postId,
                                    @PathVariable("id") Long commentId,
                              @RequestBody @Valid CommentDTO commentDTO ) {
        Post post = postService.findOrElseThrow(postId);
        Comment comment = commentService.findOrElseThrow(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new PostNotFoundException(postId);
        }

       /* comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());*/
        BeanUtils.copyProperties(commentDTO, comment, "id");

        comment = commentService.createComment(comment);

        return commentMapper.convertToDto(comment);
    }

    @DeleteMapping("/{postId}/comment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("postId") Long postId,
                              @PathVariable("id") Long commentId) {
        Post post = postService.findOrElseThrow(postId);
        Comment comment = commentService.findOrElseThrow(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new PostNotFoundException(postId);
        }
        commentService.deleteComment(comment.getId());
    }
}
