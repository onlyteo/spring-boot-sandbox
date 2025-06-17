package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured with form login.
     * See the JavaDoc of the [HttpSecurity.formLogin] method for more details.
     *
     * @param http       - HTTP security configuration builder.
     * @param applicationProperties - Custom security properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Throws(Exception::class)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        applicationProperties: ApplicationProperties
    ): SecurityFilterChain {
        return http
            //.csrf { it.disable() }
            .csrf { config ->
                config.requireCsrfProtectionMatcher(LOGIN_POST_REQUEST_MATCHER)
            }
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(
                        "/error",
                        "/favicon.ico",
                        "/assets/**",
                        "/webjars/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { config ->
                config
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .permitAll()
            }
            .logout { config ->
                config
                    .logoutRequestMatcher(LOGOUT_REQUEST_MATCHER)
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
            }
            .build()
    }

    @Bean
    fun userDetailsService(
        jdbcTemplate: JdbcTemplate,
        passwordEncoder: PasswordEncoder,
        applicationProperties: ApplicationProperties
    ): UserDetailsService {
        val userDetailsService = JdbcUserDetailsManager()
            .apply { setJdbcTemplate(jdbcTemplate) }
        applicationProperties.security.users
            .map {
                User.withUsername(it.username)
                    .password(passwordEncoder.encode(it.password))
                    .roles(*it.roles.toTypedArray())
                    .build()
            }
            .forEach(userDetailsService::createUser)
        return userDetailsService
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    companion object {
        val LOGIN_POST_REQUEST_MATCHER: RequestMatcher = PathPatternRequestMatcher
            .withDefaults()
            .matcher(HttpMethod.POST, "/login")
        val LOGOUT_REQUEST_MATCHER: RequestMatcher = PathPatternRequestMatcher
            .withDefaults()
            .matcher("/logout")
    }
}