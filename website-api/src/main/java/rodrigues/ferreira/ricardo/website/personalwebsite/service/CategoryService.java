package rodrigues.ferreira.ricardo.website.personalwebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;
import rodrigues.ferreira.ricardo.website.personalwebsite.exception.CategoryNotFoundException;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category addCategory(Category category){
        if(findCategoryByName(category.getName()).isPresent()) {
            throw new CategoryNotFoundException(String.format("The category %s already exist!", category.getName()));
        }
       return repository.save(category);
    }

    public Category getCategory(Long id){
        return findOrElseThrow(id);
    }

    public void deleteCategory(Long id){
        Category category = findOrElseThrow(id);
        repository.delete(category);
    }

    public Optional<Category> findCategoryByName(String name){
        return repository.findByName(name);
    }

    public List<Category> findAllCategories(){
        return repository.findAll();
    }

    public Category findOrElseThrow(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));;
        repository.flush();
        return category;
    }
}
