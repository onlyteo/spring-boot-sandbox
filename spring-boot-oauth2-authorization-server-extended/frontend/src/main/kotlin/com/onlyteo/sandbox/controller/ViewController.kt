package com.onlyteo.sandbox.controller

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class ViewController {

    @GetMapping(path = ["/"])
    fun getIndexPage(): String {
        return "index"
    }

    @ModelAttribute
    fun getModel(
        authentication: Authentication?,
        model: Model
    ) {
        if (authentication != null && authentication.principal is OidcUser) {
            model.addAttribute("profile", authentication.principal)
        }
    }
}