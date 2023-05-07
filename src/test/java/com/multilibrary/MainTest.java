package com.multilibrary;

import com.multilibrary.models.Author;
import com.multilibrary.models.Books;
import com.multilibrary.models.Reviews;
import com.multilibrary.models.Users;
import com.multilibrary.models.enums.Genre;
import com.multilibrary.models.enums.Role;
import com.multilibrary.repo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MainTest {

    @Autowired
    private RepoUsers repoUsers;
    @Autowired
    private RepoBooks repoBooks;
    @Autowired
    private RepoAuthor repoAuthor;
    @Autowired
    private RepoReviews repoReviews;

    public String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    @Test
    void UserAdd() {
        String username = "test";
        String password = "test";
        Role role = Role.USER;

        Users user = repoUsers.saveAndFlush(new Users(username, password, role));

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getUsername(), username);
        Assertions.assertEquals(user.getRole(), role);

        Assertions.assertNotNull(repoUsers.getReferenceById(user.getId()));
    }

    @Test
    void ReviewAdd() {
        String text = "test";
        String tel = "test";
        String date = DateNow();
        Users user = repoUsers.findAll().get(0);
        Reviews review = repoReviews.saveAndFlush(new Reviews(text, tel, date, user));

        Assertions.assertNotNull(review);
        Assertions.assertEquals(review.getText(), text);
        Assertions.assertEquals(review.getTel(), tel);
        Assertions.assertEquals(review.getDate(), date);
        Assertions.assertEquals(review.getOwner().getId(), user.getId());

        Assertions.assertNotNull(repoReviews.getReferenceById(review.getId()));
    }

    @Test
    void AuthorAdd() {
        String name = "testAuthor";
        String poster = "def/bg.jpg";
        int birthday = 1900;
        int die = 1950;
        String description = "Some text";

        Author author = repoAuthor.save(new Author(name, birthday, die, poster, description));

        Assertions.assertNotNull(author);

        Assertions.assertEquals(author.getName(), name);
        Assertions.assertEquals(author.getPoster(), poster);
        Assertions.assertEquals(author.getBirthday(), birthday);
        Assertions.assertEquals(author.getDie(), die);
        Assertions.assertEquals(author.getDescription(), description);

        Assertions.assertNotNull(repoAuthor.getReferenceById(author.getId()));
    }

    @Test
    void BookAdd() {
        Author author = repoAuthor.findAll().get(0);
        String name = "testBook";
        String poster = "def/bg.jpg";
        String[] screenshots = {"def/bg.jpg"};
        String pub = "TestPublisher";
        String isbn = "Test1234";
        int year = 1925;
        float price = 4.99f;
        float weight = 0.3f;
        Genre genre = Genre.Бизнес;
        String description = "Some text";
        String link = "https://www.youtube.com/";

        Books book = repoBooks.save(new Books(name, author.getName(), pub, isbn, year, price, weight, genre, author.getId(), poster, screenshots, description,link));

        Assertions.assertNotNull(book);

        Assertions.assertEquals(book.getName(), name);
        Assertions.assertEquals(book.getPoster(), poster);
        Assertions.assertEquals(book.getAuthor(), author.getName());
        Assertions.assertEquals(book.getAuthorid(), author.getId());
        Assertions.assertEquals(book.getScreenshots(), screenshots);
        Assertions.assertEquals(book.getPub(), pub);
        Assertions.assertEquals(book.getIsbn(), isbn);
        Assertions.assertEquals(book.getYear(), year);
        Assertions.assertEquals(book.getPrice(), price);
        Assertions.assertEquals(book.getWeight(), weight);
        Assertions.assertEquals(book.getGenre(), genre);
        Assertions.assertEquals(book.getDescription(), description);
        Assertions.assertEquals(book.getLink(), link);

        Assertions.assertNotNull(repoBooks.getReferenceById(book.getId()));
    }
}
