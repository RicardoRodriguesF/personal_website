package rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.AuthorResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.input.PostRequest;
import rodrigues.ferreira.ricardo.website.personalwebsite.controller.output.PostResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostShortDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PostMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PostResponse convertToDto(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }
    public PostResponse convertToDtoWithAuthor(Post post, AuthorResponse author) {
        post.setAuthorId(author.getId());
        post.setAuthorName(author.getName());
        if (author.getId() == null) {
            post.setAuthorId(0L);
            post.setAuthorName("anonymous");
        }
        return modelMapper.map(post, PostResponse.class);
    }

    public PostShortDTO convertToShortDto(Post post) {
        post.setContent(post.getContent().substring(0, 10));
        return modelMapper.map(post, PostShortDTO.class);
    }

    public Post convertToEntity(PostRequest postRequest) {
        return modelMapper.map(postRequest, Post.class);
    }

    public List<PostResponse> toCollectionDto(List<Post> postList) {
        return postList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<PostShortDTO> toCollectionShortDto(List<Post> postList) {
        return postList.stream().map(this::convertToShortDto).collect(Collectors.toList());
    }

    public void copyToDomainObject(PostRequest postRequest, Post post) {
        post.setCategory(new Category());
        post.setUpdatedOn(java.time.Instant.now());
        modelMapper.map(postRequest, post);
    }
}

