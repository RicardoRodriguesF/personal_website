package rodrigues.ferreira.ricardo.authorization.authorization.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodrigues.ferreira.ricardo.authorization.authorization.server.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String username);
}
