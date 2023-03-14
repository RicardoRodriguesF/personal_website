package rodrigues.ferreira.ricardo.website.personalwebsite.controller.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.StatusPost;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Long postId;

    @NotEmpty
    @Size(min = 2)
    private String title;

    @NotEmpty
    @Size(min = 10)
    private String description;

    @NotEmpty
    private String content;

    private Long categoryId;

    private StatusPost statusPost;

}
