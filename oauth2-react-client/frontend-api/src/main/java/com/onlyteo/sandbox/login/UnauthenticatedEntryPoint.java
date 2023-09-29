package com.onlyteo.sandbox.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UnauthenticatedEntryPoint implements AuthenticationEntryPoint {

    private static final String OAUTH2_LOGIN_BASE_PATH = "/oauth2/authorization";
    private final URI redirectUri;

    public UnauthenticatedEntryPoint(final String registeredClientId) {
        Assert.hasText(registeredClientId, "Registered client ID is null or blank");
        this.redirectUri = UriComponentsBuilder.fromPath(OAUTH2_LOGIN_BASE_PATH)
                .pathSegment(registeredClientId)
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
