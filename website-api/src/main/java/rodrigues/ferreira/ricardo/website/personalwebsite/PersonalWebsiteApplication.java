package rodrigues.ferreira.ricardo.website.personalwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import rodrigues.ferreira.ricardo.website.personalwebsite.repository.impl.CustomJpaRepositoryImpl;

@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)

public class PersonalWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalWebsiteApplication.class, args);
	}


}
