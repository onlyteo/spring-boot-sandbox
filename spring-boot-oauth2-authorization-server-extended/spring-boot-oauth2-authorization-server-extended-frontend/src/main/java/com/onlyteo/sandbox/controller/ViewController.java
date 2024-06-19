package com.onlyteo.sandbox.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ViewController {

    @GetMapping(path = "/")
    public String getIndexPage() {
        return "index";
    }

    @ModelAttribute
    public void getModel(final Authentication authentication,
                         final Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser principal) {
            model.addAttribute("profile", principal);
        }
    }
}
