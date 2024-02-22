package com.onlyteo.sandbox.cache;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.util.StringUtils;

/**
 * Custom request cache implementation that will save the URL from the {@code Referer} HTTP header. If the
 * {@code Referer} header is not present in the request it will save the original request URL.
 */
public class ReferrerAwareHttpSessionRequestCache extends HttpSessionRequestCache {

    private static final String SAVED_REQUEST_ATTRIBUTE_NAME = "SPRING_SECURITY_SAVED_REQUEST";

    public ReferrerAwareHttpSessionRequestCache() {
        setSessionAttrName(SAVED_REQUEST_ATTRIBUTE_NAME);
    }

    @Override
    public void saveRequest(final HttpServletRequest request, final HttpServletResponse response) {
        final var referrer = request.getHeader(HttpHeaders.REFERER);
        final var redirectUrl = StringUtils.hasText(referrer) ? referrer : request.getRequestURL().toString();
        request.getSession().setAttribute(SAVED_REQUEST_ATTRIBUTE_NAME, new SimpleSavedRequest(redirectUrl));
    }
}
