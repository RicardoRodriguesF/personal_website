package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CategoryDTO extends BaseDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    private String description;

    private Integer numberOfPosts;
}
