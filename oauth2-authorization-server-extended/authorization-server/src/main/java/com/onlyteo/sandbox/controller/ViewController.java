package com.onlyteo.sandbox.controller;

import com.onlyteo.sandbox.model.RegisterFormData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

    private final ConversionService conversionService;
    private final UserDetailsManager userDetailsManager;

    public ViewController(final ConversionService conversionService,
                          final UserDetailsManager userDetailsManager) {
        this.conversionService = conversionService;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping(path = "/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(path = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(path = "/register")
    public String getRegisterPage(final Model model) {
        model.addAttribute("registerFormData", new RegisterFormData(null, null, null));
        return "register";
    }

    @PostMapping(path = "/register")
    public String postRegisterPage(final Model model,
                                   @ModelAttribute @Valid @NotNull final RegisterFormData registerFormData,
                                   final BindingResult bindingResult,
                                   final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerFormData", registerFormData);
            return "register";
        }
        final var userDetails = conversionService.convert(registerFormData, UserDetails.class);
        userDetailsManager.createUser(userDetails);
        redirectAttributes.addFlashAttribute("registration", "success");
        return "redirect:/login";
    }

    @ModelAttribute
    public void modelAttribute(final Authentication authentication,
                               final Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser principal) {
            model.addAttribute("profile", principal);
        }
    }
}
