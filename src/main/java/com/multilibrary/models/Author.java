package com.multilibrary.models;

import javax.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int birthday, die;
    private String poster;
    @Column(length = 5000)
    private String description;
    private long booksid[];

    public Author() {
    }

    public Author(String name, int birthday, int die) {
        this.name = name;
        this.birthday = birthday;
        this.die = die;
    }

    public Author(String name, int birthday, int die, String poster, String description) {
        this.name = name;
        this.birthday = birthday;
        this.die = die;
        this.poster = poster;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getDie() {
        return die;
    }

    public void setDie(int die) {
        this.die = die;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long[] getBooksid() {
        return booksid;
    }

    public void setBooksid(long[] booksid) {
        this.booksid = booksid;
    }
}
