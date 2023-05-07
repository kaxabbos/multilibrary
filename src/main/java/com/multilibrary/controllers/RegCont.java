package com.multilibrary.controllers;

import com.multilibrary.controllers.main.Main;
import com.multilibrary.models.Users;
import com.multilibrary.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegCont extends Main {

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("role", getRole());
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (repoUsers.findAll().size() == 0 || repoUsers.findAll().isEmpty()) {
            repoUsers.save(new Users(username, password, Role.ADMIN));
            return "redirect:/login";
        }
        Users userFromDB = repoUsers.findByUsername(username);
        if (userFromDB != null) {
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Пользователь c таким именем уже существует существует!");
            return "reg";
        }
        repoUsers.save(new Users(username, password, Role.USER));

        return "redirect:/login";
    }
}
