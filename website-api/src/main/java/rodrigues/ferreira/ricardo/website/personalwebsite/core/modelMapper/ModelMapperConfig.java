package rodrigues.ferreira.ricardo.website.personalwebsite.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMap() {
        return new ModelMapper();
    }

}
