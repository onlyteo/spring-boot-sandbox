package com.onlyteo.sandbox.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.onlyteo.sandbox.mapper.OAuth2AuthorizationServerPropertiesMapper;
import com.onlyteo.sandbox.properties.AppSecurityProperties;
import com.onlyteo.sandbox.serialize.PrivateKeyDeserializer;
import com.onlyteo.sandbox.serialize.PublicKeyDeserializer;
import com.onlyteo.sandbox.service.JwkService;
import com.onlyteo.sandbox.service.KeyService;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /**
     * The {@link SecurityFilterChain} defines how the application should be protected using Spring Security.
     * By using the DSL of the {@link HttpSecurity} configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Authorization Server extended with Open ID Connect capabilities.
     * This customizes the basic OAuth2 Resource Server configuration with Authorization Server capabilities.
     * See the JavaDoc of the {@link HttpSecurity#formLogin} method for more details.
     *
     * @param http - HTTP security configuration builder.
     * @return The {@link SecurityFilterChain} bean.
     * @throws Exception -
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0
        return http
                .exceptionHandling(config -> config
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()))
                .build();
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory {@link OAuth2AuthorizationConsentService}. This changes
     * to use a JDBC variant so that {@link OAuth2AuthorizationConsent} instances are stored in a database.
     *
     * @param jdbcTemplate               - The default {@link JdbcTemplate} bean.
     * @param registeredClientRepository - The {@link RegisteredClientRepository} bean from below.
     * @return The {@link OAuth2AuthorizationConsentService} bean.
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(final JdbcTemplate jdbcTemplate,
                                                                         final RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory {@link OAuth2AuthorizationService}. This changes
     * to use a JDBC variant so that {@link OAuth2Authorization} instances are stored in a database.
     *
     * @param jdbcTemplate               - The default {@link JdbcTemplate} bean.
     * @param registeredClientRepository - The {@link RegisteredClientRepository} bean from below.
     * @return The {@link JdbcOAuth2AuthorizationService} bean.
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(final JdbcTemplate jdbcTemplate,
                                                           final RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory {@link RegisteredClientRepository}. This changes
     * to use a JDBC variant so that {@link RegisteredClient} instances are stored in a database.
     *
     * @param jdbcTemplate - The default {@link JdbcTemplate} bean.
     * @param properties   -
     * @return The {@link JdbcRegisteredClientRepository} bean.
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(final JdbcTemplate jdbcTemplate,
                                                                 final OAuth2AuthorizationServerProperties properties) {
        final var propertiesMapper = new OAuth2AuthorizationServerPropertiesMapper(properties);
        final var registeredClients = propertiesMapper.asRegisteredClients();
        final var registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        registeredClients.forEach(registeredClientRepository::save);
        return registeredClientRepository;
    }

    /**
     * Override the {@link JwtDecoder} to use the custom {@link JWKSource} from below.
     *
     * @param jwkSource - The {@link JWKSource} bean from below.
     * @return The {@link JwtDecoder} bean.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory {@link JWKSource} that generates new
     * JSON Web Keys each time the application starts. The JSON Web Keys are used for both signing and decoding
     * JSON Web Tokens, but also for exposing the Open ID Connect JWK endpoint.
     * By overriding the {@link JWKSource} with this custom service the keys are loaded from static keys on the classpath.
     *
     * @param securityProperties - Custom security properties.
     * @param keyService         - The {@link KeyService} bean from below.
     * @return The {@link JwkService} bean.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(final AppSecurityProperties securityProperties,
                                                final KeyService keyService) {
        return new JwkService(securityProperties, keyService);
    }

    /**
     * A service for loading key resources from file or classpath.
     *
     * @param resourceLoader         - Utility bean for loading resources.
     * @param publicKeyDeserializer  - Deserializer bean for public keys.
     * @param privateKeyDeserializer - Deserializer bean for private keys.
     * @return The {@link KeyService} bean.
     */
    @Bean
    public KeyService keyService(final ResourceLoader resourceLoader,
                                 final PublicKeyDeserializer publicKeyDeserializer,
                                 final PrivateKeyDeserializer privateKeyDeserializer) {
        return new KeyService(resourceLoader, publicKeyDeserializer, privateKeyDeserializer);
    }

    /**
     * An RSA {@link KeyFactory} bean.
     *
     * @return The {@link KeyFactory} bean.
     * @throws NoSuchAlgorithmException -
     */
    @Bean
    public KeyFactory rsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }
}
