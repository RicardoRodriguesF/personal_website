package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import org.springframework.stereotype.Repository;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category>{
    Optional<Category> findByName(String name);
}
