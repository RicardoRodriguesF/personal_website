package rodrigues.ferreira.ricardo.website.personalwebsite.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.PostRequest;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.output.PostResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Vote;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.VoteType;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.CommentRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.VoteRepository;
import rodrigues.ferreira.ricardo.website.personalwebsite.security.SecurityService;

import java.util.Optional;

import static rodrigues.ferreira.ricardo.website.personalwebsite.entity.VoteType.DOWNVOTE;
import static rodrigues.ferreira.ricardo.website.personalwebsite.entity.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class MapperPost {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserReactiveClient userReactiveClient;
    @Autowired
    private SecurityService securityService;


    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedOn", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "authorId", source = "user.id")
    @Mapping(target = "authorName", source = "user.name")
    public abstract Post map(PostRequest postRequest, Category category, UserResponse user);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "author", source = "authorName")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedOn().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        UserResponse user = userReactiveClient.findById(securityService.getUserId()).block();

        if (user != null) {

            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserIdOrderByVoteIdDesc(post, user.getId());

            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}