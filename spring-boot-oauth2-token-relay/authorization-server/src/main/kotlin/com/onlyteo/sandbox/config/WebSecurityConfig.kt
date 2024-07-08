package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.AppSecurityProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
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
     */
    @Bean
    @Order(2)
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
}