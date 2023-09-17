package com.example.preventduplicatelogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/duplicated-login")
    public String getDuplicatedLogin() {
        return "duplicated-login";
    }
}
