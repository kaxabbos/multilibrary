package com.multilibrary.controllers;

import com.multilibrary.controllers.main.Main;
import com.multilibrary.models.Author;
import com.multilibrary.models.Books;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/author")
public class AuthorCont extends Main {

    @GetMapping
    public String authors(Model model) {
        List<Author> authors = repoAuthor.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("role", getRole());
        return "authors";
    }

    @GetMapping("/{id}")
    public String author(Model model, @PathVariable(value = "id") Long id) {
        Optional<Author> temp = repoAuthor.findById(id);
        List<Author> author = new ArrayList<>();
        temp.ifPresent(author::add);

        List<Books> booksList = repoBooks.findAllByAuthorid(id);

        model.addAttribute("authors", author);
        model.addAttribute("books", booksList);
        model.addAttribute("role", getRole());
        return "author";
    }

    @GetMapping("/add")
    public String author_add(Model model) {
        model.addAttribute("role", getRole());
        return "author_add";
    }

    @PostMapping("/add")
    public String author_add(
            @RequestParam String name, @RequestParam("poster") MultipartFile poster,
            @RequestParam int birthday, @RequestParam int die, @RequestParam String description
    ) throws IOException {
        Author newAuthor = new Author(name, birthday, die);

        newAuthor.setDescription(description);

        String uuidFile = UUID.randomUUID().toString();

        if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result_poster = uuidFile + "_" + poster.getOriginalFilename();
            poster.transferTo(new File(uploadPath + "/" + result_poster));
            newAuthor.setPoster(result_poster);
        }

        repoAuthor.save(newAuthor);
        return "redirect:/author";
    }

    @GetMapping("/{id}/edit")
    public String author_edit(@PathVariable(value = "id") Long id, Model model) {
        if (!repoAuthor.existsById(id)) return "redirect:/authors";
        Optional<Author> temp = repoAuthor.findById(id);
        ArrayList<Author> authors = new ArrayList<>();
        temp.ifPresent(authors::add);
        model.addAttribute("authors", authors);
        model.addAttribute("role", getRole());
        return "author_edit";
    }

    @PostMapping("/{id}/edit")
    public String author_edit(@PathVariable(value = "id") Long id,
                              @RequestParam String name, @RequestParam("poster") MultipartFile poster,
                              @RequestParam int birthday, @RequestParam int die, @RequestParam String description
    ) throws IOException {
        Author author = repoAuthor.findById(id).orElseThrow();

        author.setName(name);
        author.setBirthday(birthday);
        author.setDie(die);
        author.setDescription(description);

        String uuidFile = UUID.randomUUID().toString();

        if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result_poster = uuidFile + "_" + poster.getOriginalFilename();
            poster.transferTo(new File(uploadPath + "/" + result_poster));
            author.setPoster(result_poster);
        }

        repoAuthor.save(author);
        return "redirect:/author";
    }

    @GetMapping("/author/{id}/delete")
    public String author_delete(@PathVariable(value = "id") Long id) {
        repoAuthor.deleteById(id);
        List<Books> booksList = repoBooks.findAllByAuthorid(id);
        for (Books b : booksList) {
            repoBooks.deleteById(b.getId());
            repoBookIncome.deleteById(b.getId());
        }
        return "redirect:/author";
    }

}
