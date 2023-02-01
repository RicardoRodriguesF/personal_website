package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO extends BaseDTO{

    private String name;
    private String email;
    private String body;
}
