package com.multilibrary.repo;

import com.multilibrary.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoComments extends JpaRepository<Comments, Long> {
    List<Comments> findAllByBookid(long id);
}
