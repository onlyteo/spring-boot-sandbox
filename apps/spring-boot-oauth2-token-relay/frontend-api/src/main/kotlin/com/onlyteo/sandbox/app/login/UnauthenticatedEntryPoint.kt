package com.onlyteo.sandbox.app.login

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.util.Assert
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

/**
 * Custom authentication entry point implementation that will populate the HTTP Servlet response with an
 * 401 Unauthorized HTTP status and a Location HTTP header containing the URL of the endpoint which will initiate
 * the OAuth2 login flow,
 */
class UnauthenticatedEntryPoint(properties: OAuth2ClientProperties) : AuthenticationEntryPoint {

    private val oauth2LoginBasePath: String = "/oauth2/authorization"
    private val redirectUri: URI

    init {
        Assert.notNull(properties, "OAuth2 Client Properties is null")
        Assert.notEmpty(properties.registration, "OAuth2 Client Registrations are empty")
        val registrationId = properties.registration.keys.iterator().next()
        this.redirectUri = UriComponentsBuilder.fromPath(oauth2LoginBasePath)
            .pathSegment(registrationId)
            .build()
            .toUri()
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.setHeader(HttpHeaders.LOCATION, redirectUri.toASCIIString())
    }
}