package rodrigues.ferreira.ricardo.website.personalwebsite.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("blog.email")
public class EmailProperties {

    private Implementation impl = Implementation.SMTP;

    private String from;

    private Sandbox sandbox = new Sandbox();

    public enum Implementation {
        SMTP, FAKE
    }

    @Getter
    @Setter
    public class Sandbox {

        private String from;

    }
}
