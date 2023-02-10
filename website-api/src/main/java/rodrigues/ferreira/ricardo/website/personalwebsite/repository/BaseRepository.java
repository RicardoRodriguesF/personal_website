/**
 * 
 */
package rodrigues.ferreira.ricardo.website.personalwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.BaseEntity;

/**
 * See this link as a reference to Spring Data repositories:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 * 
 * By declaring an interface which extends one of the spring data repositories
 * we get out-of-the-box functionality which allows us to easily have access to 
 * advanced CRUD operations.
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>
	extends JpaRepository<T, Long> {


}
