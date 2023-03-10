package rodrigues.ferreira.ricardo.website.personalwebsite.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeSendEmailService implements SendEmailService {

	@Autowired
	private ProcessedEmailTemplate emailTemplate;

	@Override
	public void sendEmail(Message message) {
		String body = emailTemplate.processedTemplate(message);

		log.info("[FAKE E-MAIL] Content: {}\n{}", message.getTo(), body);

	}

}
