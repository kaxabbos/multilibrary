package com.multilibrary.controllers;

import com.multilibrary.models.Books;
import com.multilibrary.models.Users;
import com.multilibrary.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    @Autowired
    RepoUsers repoUsers;

    @Autowired
    RepoBooks repoBooks;

    @Autowired
    RepoComments repoComments;

    @Autowired
    RepoBookIncome repoBookIncome;

    @Autowired
    RepoAuthor repoAuthor;
    @Autowired
    RepoReviews repoReviews;

    @Value("${upload.path}")
    String uploadPath;


    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return repoUsers.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }

    public String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}
