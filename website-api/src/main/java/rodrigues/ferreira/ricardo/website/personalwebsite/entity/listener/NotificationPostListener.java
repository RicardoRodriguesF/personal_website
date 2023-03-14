package rodrigues.ferreira.ricardo.website.personalwebsite.entity.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserReactiveClient;
import rodrigues.ferreira.ricardo.website.personalwebsite.client.UserResponse;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.Post;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.User;
import rodrigues.ferreira.ricardo.website.personalwebsite.entity.event.PostPublishedEvent;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.SendEmailService;
import rodrigues.ferreira.ricardo.website.personalwebsite.service.email.SendEmailService.Message;

import java.util.Collections;

@Component
public class NotificationPostListener {

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private UserReactiveClient userReactiveClient;

    @TransactionalEventListener()
    public void sendNotification(PostPublishedEvent event) {
        Post post = event.getPost();

        UserResponse user = userReactiveClient.findById(post.getAuthorId()).block();

        var message = Message.builder()
                .subject(post.getTitle() + " - was published")
                .body("emails/post-published.html")
                .variable("post", post)
                .variable("user", user)
                .to(Collections.singleton(user.getEmail()))
                .build();

        sendEmailService.sendEmail(message);
    }
}
