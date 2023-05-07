package com.multilibrary.models;

import com.multilibrary.models.enums.Genre;

import javax.persistence.*;

@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userid;
    private long authorid;
    private String name;
    private String author;
    private String pub;
    private String isbn;
    @Column(length = 5000)
    private String description;

    private String poster;
    private String[] screenshots;

    private int year;

    private float price, weight;

    private Genre genre;

    public Books() {
    }

    public Books(
            String name, String author, String pub, String isbn,
            int year, float price, float weight, Genre genre, long authorid
    ) {
        this.name = name;
        this.author = author;
        this.pub = pub;
        this.isbn = isbn;
        this.year = year;
        this.price = price;
        this.weight = weight;
        this.genre = genre;
        this.authorid = authorid;
    }

    public Books(
            String name, String author, String pub, String isbn,
            int year, float price, float weight, Genre genre, long authorid,
            String poster, String[] screenshots, String description
    ) {
        this.name = name;
        this.author = author;
        this.pub = pub;
        this.isbn = isbn;
        this.year = year;
        this.price = price;
        this.weight = weight;
        this.genre = genre;
        this.authorid = authorid;
        this.poster = poster;
        this.screenshots = screenshots;
        this.description = description;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public long getId() {
        return id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userId) {
        this.userid = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(String[] screenshots) {
        this.screenshots = screenshots;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
