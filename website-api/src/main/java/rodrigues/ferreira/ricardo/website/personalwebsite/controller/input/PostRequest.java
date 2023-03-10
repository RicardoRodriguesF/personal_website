package rodrigues.ferreira.ricardo.website.personalwebsite.controller.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.BaseDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CommentDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.StatusPost;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
public class PostRequest extends BaseDTO {

    @NotEmpty
    @Size(min = 2)
    private String title;

    @NotEmpty
    @Size(min = 10)
    private String description;

    @NotEmpty
    private String content;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate createdOn;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate updatedOn;

    private Long categoryId;

    private Long authorId;

    private StatusPost statusPost;

}
