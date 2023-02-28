package rodrigues.ferreira.ricardo.website.personalwebsite.controller.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;

@Setter
@Getter
@AllArgsConstructor
public class AuthorResponse {

    private Long id;
    private String name;

    public static AuthorResponse of(UserResponse editor) {
        return new AuthorResponse(editor.getId(),editor.getName());
    }

    public static AuthorResponse anonymousEditor(Long id){
        return new AuthorResponse(id,"Anonymus");
    }
}
