package rodrigues.ferreira.ricardo.website.personalwebsite.service.email;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import rodrigues.ferreira.ricardo.website.personalwebsite.core.email.EmailProperties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SmtpSendEmailService implements SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private ProcessedEmailTemplate processedEmailTemplate;

    @Override
    public void sendEmail(Message message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        String body = processedEmailTemplate.processedTemplate(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getFrom());
        helper.setTo(message.getTo().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);

        return mimeMessage;
    }
}
