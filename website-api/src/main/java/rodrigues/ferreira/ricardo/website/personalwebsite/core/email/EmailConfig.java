package rodrigues.ferreira.ricardo.website.personalwebsite.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.FakeSendEmailService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.SendEmailService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.SmtpSendEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public SendEmailService sendEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeSendEmailService();
			case SMTP:
				return new SmtpSendEmailService();
			default:
				return null;
		}
	}

}