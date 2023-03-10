package rodrigues.ferreira.ricardo.website.personalwebsite.entity.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

@Getter
@AllArgsConstructor
public class PostPublishedEvent {

    private Post post;
}
