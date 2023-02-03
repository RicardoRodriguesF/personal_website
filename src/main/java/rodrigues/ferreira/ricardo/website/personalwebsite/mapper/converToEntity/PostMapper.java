package rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PostDTO convertToDto(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    public Post convertToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    public List<PostDTO> toCollectionDto(List<Post> postList) {
        return postList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void copyToEntity(PostDTO postDTO, Post post) {
        modelMapper.map(postDTO, post);
    }
}

