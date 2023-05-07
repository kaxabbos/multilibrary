package com.multilibrary.controllers;

import com.multilibrary.controllers.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainCont extends Main {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("role", getRole());
        return "about";
    }

    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("role", getRole());
        return "map";
    }

    @GetMapping
    public String index1(Model model) {
        model.addAttribute("role", getRole());
        return "index";
    }

    @GetMapping("/index")
    public String index2(Model model) {
        model.addAttribute("role", getRole());
        return "index";
    }
}