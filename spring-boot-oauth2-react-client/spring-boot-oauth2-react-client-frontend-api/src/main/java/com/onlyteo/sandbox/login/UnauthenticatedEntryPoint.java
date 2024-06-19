package com.onlyteo.sandbox.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Custom authentication entry point implementation that will populate the HTTP Servlet response with an
 * 401 Unauthorized HTTP status and a Location HTTP header containing the URL of the endpoint which will initiate
 * the OAuth2 login flow,
 */
public class UnauthenticatedEntryPoint implements AuthenticationEntryPoint {

    private static final String OAUTH2_LOGIN_BASE_PATH = "/oauth2/authorization";
    private final URI redirectUri;

    public UnauthenticatedEntryPoint(final OAuth2ClientProperties properties) {
        Assert.notNull(properties, "OAuth2 Client Properties is null");
        Assert.notEmpty(properties.getRegistration(), "OAuth2 Client Registrations are empty");
        final var registrationId = properties.getRegistration().keySet().iterator().next();
        this.redirectUri = UriComponentsBuilder.fromPath(OAUTH2_LOGIN_BASE_PATH)
                .pathSegment(registrationId)
                .build()
                .toUri();
    }

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authenticationException) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.LOCATION, redirectUri.toASCIIString());
    }
}
