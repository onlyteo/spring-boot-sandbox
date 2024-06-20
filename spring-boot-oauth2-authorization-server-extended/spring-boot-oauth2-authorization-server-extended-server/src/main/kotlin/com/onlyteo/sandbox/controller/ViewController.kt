package com.onlyteo.sandbox.controller

import com.onlyteo.sandbox.model.RegisterFormData
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.core.convert.ConversionService
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ViewController(
    private val conversionService: ConversionService,
    private val userDetailsManager: UserDetailsManager
) {

    @GetMapping(path = ["/"])
    fun getIndexPage(): String {
        return "index"
    }

    @GetMapping(path = ["/login"])
    fun getLoginPage(): String {
        return "login"
    }

    @GetMapping(path = ["/register"])
    fun getRegisterPage(model: Model): String {
        model.addAttribute("registerFormData", RegisterFormData())
        return "register"
    }

    @PostMapping(path = ["/register"])
    fun postRegisterPage(
        model: Model,
        @ModelAttribute @Valid @NotNull registerFormData: RegisterFormData?,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerFormData", registerFormData)
            return "register"
        }
        val userDetails = conversionService.convert(registerFormData, UserDetails::class.java)
        userDetailsManager.createUser(userDetails)
        redirectAttributes.addFlashAttribute("registration", "success")
        return "redirect:/login"
    }

    @ModelAttribute
    fun modelAttribute(
        authentication: Authentication?,
        model: Model
    ) {
        if (authentication != null && authentication.principal is OidcUser) {
            model.addAttribute("profile", authentication.principal)
        }
    }
}