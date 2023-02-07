package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PostDTO extends BaseDTO{


    private String title;

    private String description;

    private String content;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private Set<CommentDTO> comments;

    private CategoryDTO category;

}
