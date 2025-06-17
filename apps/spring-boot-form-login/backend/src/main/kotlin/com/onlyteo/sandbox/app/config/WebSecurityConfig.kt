package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured with Basic Authentication.
     * See the JavaDoc of the [HttpSecurity.httpBasic] method for more details.
     *
     * @param http       - HTTP security configuration builder.
     * @param properties - Custom security properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Throws(Exception::class)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        properties: ApplicationProperties
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }
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
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun userDetailsService(
        passwordEncoder: PasswordEncoder,
        applicationProperties: ApplicationProperties
    ): UserDetailsService {
        val userDetailsService = InMemoryUserDetailsManager()
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
}