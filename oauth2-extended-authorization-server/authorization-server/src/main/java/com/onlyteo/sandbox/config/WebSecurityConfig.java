package com.onlyteo.sandbox.config;

import com.onlyteo.sandbox.mapper.AppSecurityPropertiesMapper;
import com.onlyteo.sandbox.properties.AppSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableConfigurationProperties(AppSecurityProperties.class)
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    /**
     * The {@link SecurityFilterChain} defines how the application should be protected using Spring Security.
     * By using the DSL of the {@link HttpSecurity} configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as with Form Login. This enables the OAuth2 Authorization Code Grant for clients.
     * See the JavaDoc of the {@link HttpSecurity#formLogin} method for more details.
     *
     * @param http               - HTTP security configuration builder.
     * @param securityProperties - Custom security properties.
     * @return The {@link SecurityFilterChain} bean.
     * @throws Exception -
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(final HttpSecurity http,
                                                      final AppSecurityProperties securityProperties) throws Exception {
        return http
                .authorizeHttpRequests(config -> config
                        .requestMatchers(securityProperties.getWhitelistedPathsAsArray()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(config -> config
                        .loginPage("/login").permitAll()
                )
                .build();
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory {@link UserDetailsManager}. This changes
     * to use a JDBC variant so that {@link UserDetails} instances are stored in a database.
     *
     * @param jdbcTemplate       - The default {@link JdbcTemplate} bean.
     * @param passwordEncoder    - The {@link PasswordEncoder} bean from below.
     * @param securityProperties - Custom security properties.
     * @return The {@link JdbcUserDetailsManager} bean.
     */
    @Bean
    public UserDetailsManager userDetailsManager(final JdbcTemplate jdbcTemplate,
                                                 final PasswordEncoder passwordEncoder,
                                                 final AppSecurityProperties securityProperties) {
        final var propertiesMapper = new AppSecurityPropertiesMapper(passwordEncoder, securityProperties);
        final var userDetails = propertiesMapper.asUserDetails();
        final var userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setJdbcTemplate(jdbcTemplate);
        userDetails.stream()
                .filter(user -> !userDetailsManager.userExists(user.getUsername()))
                .forEach(userDetailsManager::createUser);
        return userDetailsManager;
    }

    /**
     * A {@link PasswordEncoder} bean.
     *
     * @return The {@link DelegatingPasswordEncoder} bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
