package com.multilibrary.repo;

import com.multilibrary.models.BookIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoBookIncome extends JpaRepository<BookIncome, Long> {
    BookIncome findByBookid(long id);

    BookIncome findByUserid(long id);

    List<BookIncome> findAllByUserid(long id);
}
