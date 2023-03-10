package rodrigues.ferreira.ricardo.website.personalwebsite.service.email;

import lombok.*;

import java.util.Map;
import java.util.Set;

public interface SendEmailService {

    void sendEmail(Message message);

    @Getter
    @Builder
    class Message {

        private Set<String> to;

        private String subject;

        private String body;

        @Singular
        private Map<String, Object> variables;
    }

}
