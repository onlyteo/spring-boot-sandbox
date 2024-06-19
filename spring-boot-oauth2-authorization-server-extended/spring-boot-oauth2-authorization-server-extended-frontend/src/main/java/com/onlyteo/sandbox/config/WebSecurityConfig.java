package com.onlyteo.sandbox.config;

import com.onlyteo.sandbox.properties.AppSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableConfigurationProperties(AppSecurityProperties.class)
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    /**
     * The {@link SecurityFilterChain} defines how the application should be protected using Spring Security.
     * By using the DSL of the {@link HttpSecurity} configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Client Login.
     * See the JavaDoc of the {@link HttpSecurity#oauth2Login} method for more details.
     *
     * @param http                 - HTTP security configuration builder.
     * @param logoutSuccessHandler - Bean that handles the logout flow. See further description below.
     * @param securityProperties   - Custom security properties.
     * @return The {@link SecurityFilterChain} bean.
     * @throws Exception -
     */
    @Bean
    public SecurityFilterChain webSecurityFilterChain(final HttpSecurity http,
                                                      final LogoutSuccessHandler logoutSuccessHandler,
                                                      final AppSecurityProperties securityProperties) throws Exception {
        return http
                .authorizeHttpRequests(config -> config
                        .requestMatchers(securityProperties.getWhitelistedPathsAsArray()).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(config -> config
                        .logoutRequestMatcher(logoutRequestMatcher())
                        .logoutSuccessHandler(logoutSuccessHandler))
                .build();
    }

    /**
     * The default behaviour of Spring Security when making a request to the logout endpoint is to clear the
     * user session in the application. The user is however still logged in at the OAuth2 Authorization Server.
     * By using this {@link LogoutSuccessHandler} the user is also logged out of the Authorization Server by
     * redirecting the browser to the logout endpoint of the Authorization Server.
     *
     * @param clientRegistrationRepository - Repository containing OAuth2 Registered Clients.
     * @return The {@link OidcClientInitiatedLogoutSuccessHandler} bean.
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(final ClientRegistrationRepository clientRegistrationRepository) {
        return new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    }

    /**
     * The default behaviour of Spring Security when logging out is to make an HTTP POST request to the {@code /logout}
     * endpoint. This works well for regular frontends where it is possible to use a regular form to invoke logout.
     * For JavaScript based frontends it is more convenient to just navigate to the logout endpoint using HTTP GET.
     * We therefore override the logout URL request matcher to allow GET requests.
     *
     * @return The {@link AntPathRequestMatcher} object.
     */
    private RequestMatcher logoutRequestMatcher() {
        return new AntPathRequestMatcher("/logout", HttpMethod.GET.name());
    }
}
