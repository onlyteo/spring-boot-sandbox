package com.onlyteo.sandbox.resource

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api/user"])
@RestController
class UserResource {

    @GetMapping
    fun getUser(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<Any> {
        return ResponseEntity.ok(oAuth2User.attributes)
    }
}