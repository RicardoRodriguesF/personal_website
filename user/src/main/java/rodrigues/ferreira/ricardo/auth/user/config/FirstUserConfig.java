package rodrigues.ferreira.ricardo.auth.user.config;

import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;
import rodrigues.ferreira.ricardo.auth.user.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FirstUserConfig implements ApplicationRunner {
	
	private final Logger logger = LoggerFactory.getLogger(FirstUserConfig.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public FirstUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (userRepository.count() != 0) {
			return;
		}
		
		logger.info("No users found, registering standard users.");

		userRepository.save(
				new UserEntity(
						"Pedro Horta",
						"admin@email.com",
						passwordEncoder.encode("123456"),
						UserEntity.Type.ADMIN
				)
		);

		userRepository.save(
				new UserEntity(
						"Paulo Rui",
						"Paulo@email.com",
						passwordEncoder.encode("123456"),
						UserEntity.Type.CLIENT
				)
		);
	}
}
