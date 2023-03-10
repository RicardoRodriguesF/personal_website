package rodrigues.ferreira.ricardo.authorization.authorization.server.security;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwsEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import rodrigues.ferreira.ricardo.authorization.authorization.server.entity.UserEntity;
import rodrigues.ferreira.ricardo.authorization.authorization.server.repository.UserRepository;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class AuthSecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(UserRepository userRepository){
        return (context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                final User user = (User) authentication.getPrincipal();

                final UserEntity userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();
                for (GrantedAuthority authority : user.getAuthorities()) {
                    authorities.add(authority.toString());
                }
                context.getClaims().claim("user_id", userEntity.getId().toString());
                context.getClaims().claim("user_fullname", userEntity.getName());
                context.getClaims().claim("authorities", authorities);
            }

        });
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder,
                                                                 JdbcTemplate jdbcTemplate) {
        RegisteredClient apiUserClient = RegisteredClient
                .withId("1")
                .clientId("apiuser")
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("users:read")
                .scope("users:write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(5))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        RegisteredClient apiBlogClient = RegisteredClient
                .withId("2")
                .clientId("apiblog")
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:3000/authorized")
                .redirectUri("https://oidcdebugger.com/debug")
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("myuser:read")
                .scope("myuser:write")
                .scope("posts:write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(1500000))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .build();

        JdbcRegisteredClientRepository clientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

       clientRepository.save(apiBlogClient);
        clientRepository.save(apiUserClient);

        return clientRepository;
      /*  return new InMemoryRegisteredClientRepository(Arrays.asList(apiUserClient, apiBlogClient));*/
    }

    @Bean
    public OAuth2AuthorizationService auth2AuthorizationService(JdbcOperations jdbcOperations,
                                                                RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(
                jdbcOperations,
                registeredClientRepository
        );
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcOperations jdbcOperations,
                                                                               RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(
                jdbcOperations,
                registeredClientRepository
        );
    }
    @Bean
    public ProviderSettings providerSettings(AuthProperties authProperties) {
        return ProviderSettings.builder()
                .issuer(authProperties.getProviderUri())
                .build();
    }

    @Bean
    public JWKSet jwkSet(AuthProperties authProperties) throws Exception {
        final var jksProperties = authProperties.getJks();
        final String jksPath = jksProperties.getPath();
        final InputStream inputStream = new ClassPathResource(jksPath).getInputStream();

        final KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, jksProperties.getStorepass().toCharArray());

        RSAKey rsaKey = RSAKey.load(keyStore,
                jksProperties.getAlias(),
                jksProperties.getKeypass().toCharArray());

        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwsEncoder(jwkSource);
    }

}
