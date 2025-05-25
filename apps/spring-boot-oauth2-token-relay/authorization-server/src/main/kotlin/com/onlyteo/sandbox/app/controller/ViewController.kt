package com.onlyteo.sandbox.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping(path = ["/"])
    fun getIndexPage(): String {
        return "index"
    }

    @GetMapping(path = ["/login"])
    fun getLoginPage(): String {
        return "login"
    }

    @GetMapping(path = ["/error"])
    fun getErrorPage(): String {
        return "error"
    }
}