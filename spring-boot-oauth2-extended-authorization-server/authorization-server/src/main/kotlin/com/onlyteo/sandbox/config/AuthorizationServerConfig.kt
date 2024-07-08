package com.onlyteo.sandbox.config

import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.onlyteo.sandbox.mapper.OAuth2AuthorizationServerPropertiesMapper
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.serialize.PrivateKeyDeserializer
import com.onlyteo.sandbox.serialize.PublicKeyDeserializer
import com.onlyteo.sandbox.service.JwkService
import com.onlyteo.sandbox.service.KeyService
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.util.function.Consumer

@Configuration(proxyBeanMethods = false)
class AuthorizationServerConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Authorization Server extended with Open ID Connect capabilities.
     * This customizes the basic OAuth2 Resource Server configuration with Authorization Server capabilities.
     * See the JavaDoc of the [OAuth2AuthorizationServerConfigurer] class for more details.
     *
     * @param http - HTTP security configuration builder.
     * @return The [SecurityFilterChain] bean.
     */
    @Bean
    @Order(1)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)
        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
        return http
            .exceptionHandling { config ->
                config
                    .defaultAuthenticationEntryPointFor(
                        LoginUrlAuthenticationEntryPoint("/login"),
                        MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            }
            .oauth2ResourceServer { config -> config.jwt(Customizer.withDefaults()) }
            .build()
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [OAuth2AuthorizationConsentService]. This changes
     * to use a JDBC variant so that [OAuth2AuthorizationConsent] instances are stored in a database.
     *
     * @param jdbcTemplate               - The default [JdbcTemplate] bean.
     * @param registeredClientRepository - The [RegisteredClientRepository] bean from below.
     * @return The [OAuth2AuthorizationConsentService] bean.
     */
    @Bean
    fun authorizationConsentService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationConsentService {
        return JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository)
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [OAuth2AuthorizationService]. This changes
     * to use a JDBC variant so that [OAuth2Authorization] instances are stored in a database.
     *
     * @param jdbcTemplate               - The default [JdbcTemplate] bean.
     * @param registeredClientRepository - The [RegisteredClientRepository] bean from below.
     * @return The [JdbcOAuth2AuthorizationService] bean.
     */
    @Bean
    fun authorizationService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationService {
        return JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository)
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [RegisteredClientRepository]. This changes
     * to use a JDBC variant so that [RegisteredClient] instances are stored in a database.
     *
     * @param jdbcTemplate - The default [JdbcTemplate] bean.
     * @param properties   - Authorization server properties.
     * @return The [JdbcRegisteredClientRepository] bean.
     */
    @Bean
    fun registeredClientRepository(
        jdbcTemplate: JdbcTemplate,
        properties: OAuth2AuthorizationServerProperties
    ): RegisteredClientRepository {
        val propertiesMapper = OAuth2AuthorizationServerPropertiesMapper(properties)
        val registeredClients = propertiesMapper.asRegisteredClients()
        val registeredClientRepository = JdbcRegisteredClientRepository(jdbcTemplate)
        registeredClients.forEach(Consumer { registeredClientRepository.save(it) })
        return registeredClientRepository
    }

    /**
     * Override the [JwtDecoder] to use the custom [JWKSource] from below.
     *
     * @param jwkSource - The [JWKSource] bean from below.
     * @return The [JwtDecoder] bean.
     */
    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [JWKSource] that generates new
     * JSON Web Keys each time the application starts. The JSON Web Keys are used for both signing and decoding
     * JSON Web Tokens, but also for exposing the Open ID Connect JWK endpoint.
     * By overriding the [JWKSource] with this custom service the keys are loaded from static keys on the classpath.
     *
     * @param properties - Custom security properties.
     * @param keyService - The [KeyService] bean from below.
     * @return The [JwkService] bean.
     */
    @Bean
    fun jwkSource(
        properties: ApplicationProperties,
        keyService: KeyService
    ): JWKSource<SecurityContext> {
        return JwkService(properties, keyService)
    }

    /**
     * A service for loading key resources from file or classpath.
     *
     * @param resourceLoader         - Utility bean for loading resources.
     * @param publicKeyDeserializer  - Deserializer bean for public keys.
     * @param privateKeyDeserializer - Deserializer bean for private keys.
     * @return The [KeyService] bean.
     */
    @Bean
    fun keyService(
        resourceLoader: ResourceLoader,
        publicKeyDeserializer: PublicKeyDeserializer,
        privateKeyDeserializer: PrivateKeyDeserializer
    ): KeyService {
        return KeyService(resourceLoader, publicKeyDeserializer, privateKeyDeserializer)
    }

    /**
     * An RSA [KeyFactory] bean.
     *
     * @return The [KeyFactory] bean.
     * @throws NoSuchAlgorithmException -
     */
    @Bean
    @Throws(NoSuchAlgorithmException::class)
    fun rsaKeyFactory(): KeyFactory {
        return KeyFactory.getInstance("RSA")
    }
}