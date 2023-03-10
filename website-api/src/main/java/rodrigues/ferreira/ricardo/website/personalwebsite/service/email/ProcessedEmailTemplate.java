package rodrigues.ferreira.ricardo.website.personalwebsite.service.email;

import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.SendEmailService.Message;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ProcessedEmailTemplate {

	@Autowired
	private Configuration freemarkerConfig;
	
	protected String processedTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("Could not mount email template", e);
		}
	}
	
}
