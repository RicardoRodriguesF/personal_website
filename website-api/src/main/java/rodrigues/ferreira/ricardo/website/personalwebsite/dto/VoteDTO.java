package rodrigues.ferreira.ricardo.website.personalwebsite.dto;

import lombok.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    private VoteType voteType;
    private Long postId;
}
