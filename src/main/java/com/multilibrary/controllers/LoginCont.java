package com.multilibrary.controllers;

import com.multilibrary.controllers.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCont extends Main {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("role", getRole());
        return "login";
    }
}
