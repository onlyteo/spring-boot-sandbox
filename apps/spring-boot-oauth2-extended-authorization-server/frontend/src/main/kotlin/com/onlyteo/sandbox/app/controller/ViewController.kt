package com.onlyteo.sandbox.app.controller

import com.onlyteo.sandbox.app.model.FormData
import com.onlyteo.sandbox.app.service.GreetingService
import jakarta.validation.Valid
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ViewController(private val greetingService: GreetingService) {

    @GetMapping(path = ["/"])
    fun getIndexPage(model: Model): String {
        model.addAttribute("formData", FormData(""))
        return "index"
    }

    @PostMapping(path = ["/"])
    fun postIndexPage(
        model: Model,
        @ModelAttribute @Valid formData: FormData,
        bindingResult: BindingResult,
        @RegisteredOAuth2AuthorizedClient("sandbox-frontend") authorizedClient: OAuth2AuthorizedClient
    ): String {
        if (!bindingResult.hasErrors()) {
            val greeting = greetingService.getGreeting(authorizedClient, formData.toPerson())
            model.addAttribute("greeting", greeting)
            model.addAttribute("formData", FormData(""))
        }
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