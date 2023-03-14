package rodrigues.ferreira.ricardo.website.personalwebsite.controller.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.StatusPost;

import java.time.Instant;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long postId;

    private String title;

    private String description;

    private String content;

    private Instant createdOn;

    private Instant updatedOn;

    private String categoryName;

    private StatusPost statusPost;

    private String author;

    private Integer voteCount;

    private Integer commentCount;

    private boolean upVote;

    private boolean downVote;

    private String duration;

}
