package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.mapper.AppSecurityPropertiesMapper
import com.onlyteo.sandbox.properties.AppSecurityProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableConfigurationProperties(AppSecurityProperties::class)
@EnableWebSecurity
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
     * @param securityProperties - Custom security properties.
     * @return The [SecurityFilterChain] bean.
     * @throws Exception -
     */
    @Bean
    @Order(2)
    @Throws(Exception::class)
    fun webSecurityFilterChain(
        http: HttpSecurity,
        securityProperties: AppSecurityProperties
    ): SecurityFilterChain {
        return http
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(*securityProperties.whitelistedPaths.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { config: FormLoginConfigurer<HttpSecurity?> ->
                config
                    .loginPage("/login").permitAll()
            }
            .build()
    }

    /**
     * The default behaviour of Spring Security is to use an in-memory [UserDetailsManager]. This changes
     * to use a JDBC variant so that [UserDetails] instances are stored in a database.
     *
     * @param jdbcTemplate       - The default [JdbcTemplate] bean.
     * @param passwordEncoder    - The [PasswordEncoder] bean from below.
     * @param securityProperties - Custom security properties.
     * @return The [JdbcUserDetailsManager] bean.
     */
    @Bean
    fun userDetailsManager(
        jdbcTemplate: JdbcTemplate,
        passwordEncoder: PasswordEncoder,
        securityProperties: AppSecurityProperties
    ): UserDetailsManager {
        val propertiesMapper = AppSecurityPropertiesMapper(passwordEncoder, securityProperties)
        val userDetails = propertiesMapper.asUserDetails()
        val userDetailsManager = JdbcUserDetailsManager()
        userDetailsManager.jdbcTemplate = jdbcTemplate
        userDetails.stream()
            .filter { user: UserDetails -> !userDetailsManager.userExists(user.username) }
            .forEach { user: UserDetails? -> userDetailsManager.createUser(user) }
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