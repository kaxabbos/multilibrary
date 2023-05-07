package com.multilibrary.repo;

import com.multilibrary.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUsers extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
