package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDTO extends BaseDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    private String description;
}
