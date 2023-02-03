package rodrigues.ferreira.ricardo.website.personalwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.website.personalwebsite.dto.CategoryDTO;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.mapper.converToEntity.CategoryMapper;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.impl.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO addCategory(@RequestBody @Valid CategoryDTO categoryRequest){
        Category category = categoryMapper.convertToEntity(categoryRequest);
        category = categoryService.addCategory(category);
        return categoryMapper.convertToDto(category);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.findOrElseThrow(categoryId);
        return categoryMapper.convertToDto(category);
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return categoryMapper.toCollectionDto(categories);
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}