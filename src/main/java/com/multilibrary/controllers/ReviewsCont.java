package com.multilibrary.controllers;

import com.multilibrary.models.Reviews;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reviews")
public class ReviewsCont extends Main{

    @GetMapping
    public String Reviews(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("reviews", repoReviews.findAll());
        return "reviews";
    }


    @PostMapping("/add")
    public String ReviewsAdd(@RequestParam String text,@RequestParam String tel) {
        repoReviews.save(new Reviews(text, tel,DateNow(), getUser()));
        return "redirect:/reviews";
    }
}
