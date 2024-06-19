package com.onlyteo.sandbox.cache

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SimpleSavedRequest
import org.springframework.util.StringUtils

/**
 * Custom request cache implementation that will save the URL from the {@code Referer} HTTP header. If the
 * {@code Referer} header is not present in the request it will save the original request URL.
 */
class ReferrerAwareHttpSessionRequestCache : HttpSessionRequestCache() {

    private val sessionAttributeName = "SPRING_SECURITY_SAVED_REQUEST"

    init {
        setSessionAttrName(sessionAttributeName)
    }

    override fun saveRequest(request: HttpServletRequest, response: HttpServletResponse) {
        val referrer = request.getHeader(HttpHeaders.REFERER)
        val redirectUrl = if (StringUtils.hasText(referrer)) referrer else request.requestURL.toString()
        request.session.setAttribute(sessionAttributeName, SimpleSavedRequest(redirectUrl))
    }
}