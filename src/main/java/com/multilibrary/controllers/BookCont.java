package com.multilibrary.controllers;

import com.multilibrary.controllers.main.Main;
import com.multilibrary.models.*;
import com.multilibrary.models.enums.Genre;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/book")
public class BookCont extends Main {

    @GetMapping("/{id}")
    public String book(@PathVariable(value = "id") Long id, Model model) {
        if (!repoBooks.existsById(id)) return "redirect:/catalog";
        long userid = 0, userIdFromBD, bookid = 0, cart = 1, buy = 1;
        Users userFromDB = new Users();

        Optional<Books> temp = repoBooks.findById(id);
        List<Books> books = new ArrayList<>();
        temp.ifPresent(books::add);

        for (Books g : books) {
            userid = g.getUserid();
            bookid = g.getId();
            break;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            if (userDetail != null) userFromDB = repoUsers.findByUsername(userDetail.getUsername());
        }

        userIdFromBD = userFromDB.getId();
        if (userIdFromBD == userid) model.addAttribute("userid", userid);

        List<Comments> comments = repoComments.findAllByBookid(bookid);
        if (userFromDB.getCart() != null) {
            long[] carts = userFromDB.getCart();
            for (long c : carts)
                if (c == id) {
                    model.addAttribute("cart", cart);
                    break;
                }
        }

        if (userFromDB.getBuy() != null) {
            long[] buys = userFromDB.getBuy();
            for (long b : buys)
                if (b == id) {
                    model.addAttribute("buy", buy);
                    break;
                }
        }


        model.addAttribute("books", books);
        model.addAttribute("comments", comments);
        model.addAttribute("role", getRole());
        return "book";
    }

    @PostMapping("/{id}/comment_add")
    public String comment_add(@PathVariable(value = "id") Long id, @RequestParam String comment) {
        repoComments.save(new Comments(id, getUser().getUsername(), DateNow(), comment));
        return "redirect:/book/{id}";
    }

    @GetMapping("/add/{id}")
    public String book_add(@PathVariable(value = "id") Long id, Model model) {
        Author author = repoAuthor.findById(id).orElseThrow();

        model.addAttribute("id", id);
        model.addAttribute("author", author.getName());
        model.addAttribute("role", getRole());
        return "book_add";
    }

    @PostMapping("/add")
    public String book_add(@RequestParam long authorid,
                           @RequestParam String name, @RequestParam("poster") MultipartFile poster,
                           @RequestParam("screenshots") MultipartFile[] screenshots, @RequestParam String pub,
                           @RequestParam String author, @RequestParam String isbn,
                           @RequestParam int year, @RequestParam float price, @RequestParam float weight, @RequestParam Genre genre,
                           @RequestParam String description
    ) throws IOException {
        Books newBooks = new Books(name, author, pub, isbn, year, price, weight, genre, authorid);
        BookIncome newBookIncome = new BookIncome(name, price);

        newBooks.setDescription(description);
        String uuidFile = UUID.randomUUID().toString();

        if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result_poster = uuidFile + "_" + poster.getOriginalFilename();
            poster.transferTo(new File(uploadPath + "/" + result_poster));
            newBooks.setPoster(result_poster);
        }

        if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
            uuidFile = UUID.randomUUID().toString();
            String result_screenshot;
            String[] result_screenshots = new String[screenshots.length];
            for (int i = 0; i < result_screenshots.length; i++) {
                result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                result_screenshots[i] = result_screenshot;
            }
            newBooks.setScreenshots(result_screenshots);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            if (userDetail != null) {
                Users userFromDB = repoUsers.findByUsername(userDetail.getUsername());
                newBooks.setUserid(userFromDB.getId());
                newBookIncome.setUserid(userFromDB.getId());
            }
        }

        long i = repoBooks.save(newBooks).getId();

        Author ath = repoAuthor.getReferenceById(authorid);

        if (ath.getBooksid() == null) {
            long[] one = new long[]{i};
            ath.setBooksid(one);
        } else {
            long[] one = new long[ath.getBooksid().length + 1];
            one[one.length - 1] = i;
            ath.setBooksid(one);
        }

        newBookIncome.setBookid(i);
        repoAuthor.save(ath);
        repoBookIncome.save(newBookIncome);

        return "redirect:/catalog/all";
    }

    @GetMapping("/{id}/edit")
    public String book_edit(@PathVariable(value = "id") Long id, Model model) {
        if (!repoBooks.existsById(id)) return "redirect:/catalog";
        Optional<Books> temp = repoBooks.findById(id);
        ArrayList<Books> books = new ArrayList<>();
        temp.ifPresent(books::add);
        model.addAttribute("books", books);
        model.addAttribute("role", getRole());
        return "book_edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable(value = "id") Long id,
            @RequestParam String name, @RequestParam("poster") MultipartFile poster,
            @RequestParam("screenshots") MultipartFile[] screenshots, @RequestParam String pub,
            @RequestParam String author, @RequestParam String isbn,
            @RequestParam int year, @RequestParam float price, @RequestParam float weight, @RequestParam Genre genre,
            @RequestParam String description
    ) throws IOException {
        Books g = repoBooks.findById(id).orElseThrow();
        BookIncome bookIncome = repoBookIncome.findById(id).orElseThrow();

        g.setDescription(description);
        g.setName(name);
        g.setPub(pub);
        g.setAuthor(author);
        g.setIsbn(isbn);
        g.setYear(year);
        g.setPrice(price);
        bookIncome.setPrice(price);
        g.setWeight(weight);
        g.setGenre(genre);
        String uuidFile = UUID.randomUUID().toString();

        if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result_poster = uuidFile + "_" + poster.getOriginalFilename();
            poster.transferTo(new File(uploadPath + "/" + result_poster));
            g.setPoster(result_poster);
        }

        if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
            uuidFile = UUID.randomUUID().toString();
            String result_screenshot;
            String[] result_screenshots = new String[screenshots.length];
            for (int i = 0; i < result_screenshots.length; i++) {
                result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                result_screenshots[i] = result_screenshot;
            }
            g.setScreenshots(result_screenshots);
        }

        repoBookIncome.save(bookIncome);
        repoBooks.save(g);
        return "redirect:/book/{id}/";
    }

    @GetMapping("/{id}/delete")
    public String book_delete(@PathVariable(value = "id") Long id) {
        repoBooks.deleteById(id);

        repoBookIncome.deleteById(id);
        List<Users> users = repoUsers.findAll();

        for (Users user : users)
            if (user.getCart() != null)
                for (long carts : user.getCart())
                    if (id == carts) {
                        if (user.getCart().length == 1) user.setCart(null);
                        else {
                            long[] cart = new long[user.getCart().length - 1];
                            int i = 0;
                            for (long c : user.getCart()) {
                                if (id == c) continue;
                                cart[i] = c;
                                i++;
                            }
                            user.setCart(cart);
                        }
                    }

        repoUsers.saveAll(users);
        return "redirect:/catalog/all";
    }
}
