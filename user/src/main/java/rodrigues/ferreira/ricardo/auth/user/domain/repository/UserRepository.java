package rodrigues.ferreira.ricardo.auth.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String username);
}
