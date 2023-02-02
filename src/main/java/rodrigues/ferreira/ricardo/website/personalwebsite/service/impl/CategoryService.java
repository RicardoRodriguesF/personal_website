package rodrigues.ferreira.ricardo.website.personalwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.CategoryNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category addCategory(Category category){
       return repository.save(category);
    }


    public void DeleteCategory(Long id){
        Category category = findOrElseThrow(id);
        repository.delete(category);
    }

    public Category findOrElseThrow(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));;
        repository.flush();
        return category;
    }

    public Category getCategory(Long id){
        return findOrElseThrow(id);
    }

    public Category FindCategoryByName(String name){
        return repository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public List<Category> findAllCategories(){
        return repository.findAll();
    }
}
