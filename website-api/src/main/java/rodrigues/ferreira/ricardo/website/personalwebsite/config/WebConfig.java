package rodrigues.ferreira.ricardo.website.personalwebsite.config;


import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {

    @Bean
    public WebClient webClient(AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager) {
        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);

        var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        oauth.setDefaultClientRegistrationId("apiuser-client");
        return WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
