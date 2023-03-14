package rodrigues.ferreira.ricardo.website.personalwebsite.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.VoteDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Vote;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.PostNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.VoteException;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.PostRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.VoteRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.SecurityService;

import javax.transaction.Transactional;
import java.util.Optional;

import static rodrigues.ferreira.ricardo.website.personalwebsite.entity.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;

    @Autowired
    private UserReactiveClient userReactiveClient;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public void vote(VoteDTO voteDto) {

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        UserResponse user = userReactiveClient.findById(securityService.getUserId()).block();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserIdOrderByVoteIdDesc(post, user.getId());

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new VoteException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDTO voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .userId(securityService.getUserId())
                .build();
    }
}
