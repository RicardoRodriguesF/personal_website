package rodrigues.ferreira.ricardo.website.personalwebsite.controller.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {

    private Long id;
    private String name;

    public static AuthorResponse of(UserResponse author) {
        return new AuthorResponse(author.getId(),author.getName());
    }

}

