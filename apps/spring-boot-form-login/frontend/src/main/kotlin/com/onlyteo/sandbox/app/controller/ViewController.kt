package com.onlyteo.sandbox.app.controller

import com.onlyteo.sandbox.app.model.PersonFormData
import com.onlyteo.sandbox.app.service.GreetingService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class ViewController(
    private val greetingService: GreetingService
) {
    @GetMapping(path = ["/login"])
    fun getLoginPage(): String {
        return "login"
    }

    @GetMapping(path = ["/error"])
    fun getErrorPage(): String {
        return "error"
    }

    @GetMapping(path = ["/"])
    fun getIndexPage(model: Model): String {
        model.addAttribute("history", false)
        model.addAttribute("personFormData", PersonFormData(""))
        model.addAttribute("error", false)
        return "index"
    }

    @PostMapping(path = ["/"])
    fun postIndexPage(
        model: Model,
        @ModelAttribute @Valid @NotNull personFormData: PersonFormData?,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (personFormData == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing form fields")
        }
        model.addAttribute("personFormData", personFormData)
        model.addAttribute("error", bindingResult.hasErrors())
        if (bindingResult.hasErrors()) {
            return "index"
        } else {
            val greeting = greetingService.getGreeting(personFormData.name)
            model.addAttribute("greeting", greeting)
            return "index"
        }
    }
}