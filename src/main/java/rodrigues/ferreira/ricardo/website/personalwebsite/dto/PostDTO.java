package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO extends BaseDTO{

    private String title;

    private String description;

    private String content;
}
