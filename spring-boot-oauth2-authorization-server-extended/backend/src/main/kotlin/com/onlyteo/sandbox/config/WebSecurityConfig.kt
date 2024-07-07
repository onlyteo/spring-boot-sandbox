package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.properties.ApplicationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
class WebSecurityConfig {

    /**
     * The [SecurityFilterChain] defines how the application should be protected using Spring Security.
     * By using the DSL of the [HttpSecurity] configuration builder it is possible to customize the security
     * setup.
     * Here the application is configured as an OAuth2 Resource Server.
     * See the JavaDoc of the [HttpSecurity.oauth2Login] method for more details.
     *
     * @param http                     - HTTP security configuration builder.
     * @param securityProperties       - Custom security properties.
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
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(*properties.security.whitelistedPaths.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { config ->
                config.jwt(Customizer.withDefaults())
            }
            .build()
    }
}