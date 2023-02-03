package rodrigues.ferreira.ricardo.website.personalwebsite.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long categoryId) {
        this(String.format("Category not found with id : '%s", categoryId));
    }
}
