package rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CategoryDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO convertToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public List<CategoryDTO> toCollectionDto(List<Category> categories) {
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void copyToEntity(CategoryDTO categoryDTO, Category category) {
        modelMapper.map(categoryDTO, category);
    }
}
