package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.mapper.asUserDetailsList
import com.onlyteo.sandbox.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as with Form Login. This enables the OAuth2 Authorization Code Grant for clients.
     * See the JavaDoc of the [HttpSecurity.formLogin] method for more details.
     *
     * @param http               - HTTP security configuration builder.
     * @param properties - Custom properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Order(2)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        properties: ApplicationProperties
    ): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it.requestMatchers(*properties.security.whitelistedPaths.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { it.loginPage("/login").permitAll() }
            .build()
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [UserDetailsManager]. This changes
     * to use a JDBC variant so that [UserDetails] instances are stored in a database.
     *
     * @param jdbcTemplate       - The default [JdbcTemplate] bean.
     * @param passwordEncoder    - The [PasswordEncoder] bean from below.
     * @param properties - Custom properties.
     * @return The [JdbcUserDetailsManager] bean.
     */
    @Bean
    fun userDetailsManager(
        jdbcTemplate: JdbcTemplate,
        passwordEncoder: PasswordEncoder,
        properties: ApplicationProperties
    ): UserDetailsManager {
        val users = properties.security.asUserDetailsList(passwordEncoder::encode)
        val userDetailsManager = JdbcUserDetailsManager()
            .apply { setJdbcTemplate(jdbcTemplate) }
        users
            .filter { user -> !userDetailsManager.userExists(user.username) }
            .forEach { user -> userDetailsManager.createUser(user) }
        return userDetailsManager
    }

    /**
     * A [PasswordEncoder] bean.
     *
     * @return The [PasswordEncoder] bean.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}