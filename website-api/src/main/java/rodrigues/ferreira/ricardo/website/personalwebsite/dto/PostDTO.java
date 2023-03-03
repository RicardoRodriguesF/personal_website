package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.AuthorResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.time.LocalDate;
import java.util.Set;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO extends BaseDTO{


    private String title;

    private String description;

    private String content;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private Set<CommentDTO> comments;

    private CategoryDTO category;

    private AuthorResponse author;

}
