package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CommentDTO extends BaseDTO{

    @NotEmpty()
    private String username;

    @NotEmpty()
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10)
    private String body;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate createdOn;

}
