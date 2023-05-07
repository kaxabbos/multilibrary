package com.multilibrary.controllers.main;

import com.multilibrary.models.Users;
import com.multilibrary.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public class Main {
    @Autowired
    protected RepoUsers repoUsers;
    @Autowired
    protected RepoBooks repoBooks;
    @Autowired
    protected RepoComments repoComments;
    @Autowired
    protected RepoBookIncome repoBookIncome;
    @Autowired
    protected RepoAuthor repoAuthor;
    @Autowired
    protected RepoReviews repoReviews;
    @Value("${upload.path}")
    protected String uploadPath;


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

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}
