package rodrigues.ferreira.ricardo.website.personalwebsite.controller.output;

import lombok.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.AuthorResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.BaseDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CategoryDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CommentDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailedResponse extends BaseDTO {

    private Long id;
    private String title;

    private String description;

    private String content;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private AuthorResponse author;
/*
    private Set<Comment> comments;


    private Category category;*/


/*
    public static PostDetailedResponse of(Post post) {
        return new PostDetailedResponse(
                post.getId(), 
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                post.getCreatedOn(),
                post.getUpdatedOn(),

                post.getComments(),

                post.getCategory(),
                anonymousEditor(post.getAuthorId()));
    }

    public static PostDetailedResponse of(Post post, AuthorResponse author) {
        return new PostDetailedResponse(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                post.getCreatedOn(),
                post.getUpdatedOn(),

                post.getComments(),

                post.getCategory(),
                author);
    }*/
    
}
