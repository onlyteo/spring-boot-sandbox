package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Client Login.
     * See the JavaDoc of the [HttpSecurity.oauth2Login] method for more details.
     *
     * @param http                               - HTTP security configuration builder.
     * @param oAuth2AuthorizationRequestResolver - Bean that handles the resolution of the authorization request.
     * @param logoutSuccessHandler               - Bean that handles the logout flow. See further description below.
     * @param properties                         - Custom security properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Throws(Exception::class)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        oAuth2AuthorizationRequestResolver: OAuth2AuthorizationRequestResolver,
        logoutSuccessHandler: LogoutSuccessHandler,
        properties: ApplicationProperties
    ): SecurityFilterChain {
        return http
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(*properties.security.whitelistedPaths.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { config ->
                config
                    .authorizationEndpoint { auth ->
                        auth.authorizationRequestResolver(oAuth2AuthorizationRequestResolver)
                    }
            }
            .logout { config ->
                config
                    .logoutRequestMatcher(logoutRequestMatcher)
                    .logoutSuccessHandler(logoutSuccessHandler)
            }
            .build()
    }

    /**
     * Proof Key for Code Exchange (PKCE) is not enabled by default in OAuth 2.0 secured clients. In order to enable
     * PKCE it is necessary to override the default resolution of the authorization request. This is done by adding the
     * [OAuth2AuthorizationRequestCustomizers.withPkce] customizer.
     *
     * @param clientRegistrationRepository - Repository containing OAuth2 Registered Clients.
     * @return The [OAuth2AuthorizationRequestResolver] bean.
     */
    @Bean
    fun oAuth2AuthorizationRequestResolver(
        clientRegistrationRepository: ClientRegistrationRepository
    ): OAuth2AuthorizationRequestResolver {
        return DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository,
            DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        ).apply {
            setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce()) // Enables PKCE
        }
    }

    /**
     * The default behaviour of Spring Security when making a request to the logout endpoint is to clear the
     * user session in the application. The user is however still logged in at the OAuth2 Authorization Server.
     * By using this [LogoutSuccessHandler] the user is also logged out of the Authorization Server by
     * redirecting the browser to the logout endpoint of the Authorization Server.
     *
     * @param clientRegistrationRepository - Repository containing OAuth2 Registered Clients.
     * @return The [OidcClientInitiatedLogoutSuccessHandler] bean.
     */
    @Bean
    fun logoutSuccessHandler(
        clientRegistrationRepository: ClientRegistrationRepository
    ): LogoutSuccessHandler {
        return OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)
    }

    /**
     * The default behaviour of Spring Security when logging out is to make an HTTP POST request to the `/logout`
     * endpoint. This works well for regular frontends where it is possible to use a regular form to invoke logout.
     * For JavaScript based frontends it is more convenient to just navigate to the logout endpoint using HTTP GET.
     * We therefore override the logout URL request matcher to allow GET requests.
     */
    private val logoutRequestMatcher: RequestMatcher = AntPathRequestMatcher("/logout", HttpMethod.GET.name())
}