package com.fundzforus.server.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginOrSignUpController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/loginOrSignUp")
    public String loginOrSignUp() {
        return "loginOrSignUp";
    }
}
