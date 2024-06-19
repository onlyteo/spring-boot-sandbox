package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.cache.ReferrerAwareHttpSessionRequestCache
import com.onlyteo.sandbox.login.UnauthenticatedEntryPoint
import com.onlyteo.sandbox.properties.AppSecurityProperties
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@EnableConfigurationProperties(AppSecurityProperties::class)
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Client Login with modifications to the flow.
     * See the JavaDoc of the [HttpSecurity.oauth2Login] method for more details.
     *
     * @param http                     - HTTP security configuration builder.
     * @param requestCache             - Cache that saves request details when initiating authentication flow. See further description below.
     * @param authenticationEntryPoint - Bean that handles initiation of authentication flow. See further description below.
     * @param logoutSuccessHandler     - Bean that handles the logout flow. See further description below.
     * @param securityProperties       - Custom security properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Throws(Exception::class)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        requestCache: RequestCache?,
        authenticationEntryPoint: AuthenticationEntryPoint?,
        logoutSuccessHandler: LogoutSuccessHandler?,
        securityProperties: AppSecurityProperties
    ): SecurityFilterChain {
        return http
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(*securityProperties.whitelistedPaths.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login(Customizer.withDefaults<OAuth2LoginConfigurer<HttpSecurity>>())
            .requestCache { config: RequestCacheConfigurer<HttpSecurity?> ->
                config.requestCache(
                    requestCache
                )
            }
            .exceptionHandling { config: ExceptionHandlingConfigurer<HttpSecurity?> ->
                config.authenticationEntryPoint(
                    authenticationEntryPoint
                )
            }
            .logout { config: LogoutConfigurer<HttpSecurity?> ->
                config
                    .logoutRequestMatcher(logoutRequestMatcher())
                    .logoutSuccessHandler(logoutSuccessHandler)
            }
            .build()
    }

    /**
     * The default behavior of Spring Security is to save the original request URL in the default request cache
     * ([HttpSessionRequestCache]) and redirect back to that URL after successful OAuth2 authentication.
     * This doesn't work well for JavScript frontends where the request typically is an API call. This bean overrides
     * the default behavior to instead save the URL from the `Referer` HTTP header, if it is present in the
     * request. The `Referer` URL will be the URL of the JavScript frontend, as visible in the browser address bar.
     *
     * @return The [ReferrerAwareHttpSessionRequestCache] bean.
     */
    @Bean
    fun requestCache(): RequestCache {
        return ReferrerAwareHttpSessionRequestCache()
    }

    /**
     * The default behaviour of Spring Security when protecting the application as an OAuth2 Client is to redirect
     * unauthenticated requests to the OAuth2 Authorization Server for login. This works well for server-side rendered
     * web applications. But for applications that only provide a REST API for JavaScript based frontend like ReactJS
     * this is not the desired behavior. This [AuthenticationEntryPoint] alters that behaviour to instead return
     * a `401 Unauthorized` HTTP status together with a `Location` HTTP header containing the relative URL
     * to the OAuth2 login endpoint. When the JavScript frontend tries to fetch data from the REST API and receives a
     * 401 HTTP status it can retrieve the login URL from the `Location` HTTP header and redirect the browser to
     * that URL in order to initiate the OAuth2 Authorization Code Grant login flow.
     * @param clientProperties - OAuth2 client properties.
     * @return The [UnauthenticatedEntryPoint] bean.
     */
    @Bean
    fun authenticationEntryPoint(clientProperties: OAuth2ClientProperties?): AuthenticationEntryPoint {
        return UnauthenticatedEntryPoint(clientProperties!!)
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
    fun logoutSuccessHandler(clientRegistrationRepository: ClientRegistrationRepository?): LogoutSuccessHandler {
        return OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)
    }

    /**
     * The default behaviour of Spring Security when logging out is to make an HTTP POST request to the `/logout`
     * endpoint. This works well for regular frontends where it is possible to use a regular form to invoke logout.
     * For JavaScript based frontends it is more convenient to just navigate to the logout endpoint using HTTP GET.
     * We therefore override the logout URL request matcher to allow GET requests.
     *
     * @return The [AntPathRequestMatcher] object.
     */
    private fun logoutRequestMatcher(): RequestMatcher {
        return AntPathRequestMatcher("/logout", HttpMethod.GET.name())
    }
}