package rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CommentDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CommentDTO convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    public Comment convertToEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    public List<CommentDTO> toCollectionDto(List<Comment> commentList) {
        return commentList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void copyToEntity(CommentDTO commentDTO, Comment comment) {
        modelMapper.map(commentDTO, comment);
    }
}
