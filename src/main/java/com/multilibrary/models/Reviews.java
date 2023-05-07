package com.multilibrary.models;

import javax.persistence.*;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 5000)
    private String text;
    private String tel;
    private String date;
    @OneToOne
    private Users owner;

    public Reviews() {
    }

    public Reviews(String text,String tel, String date, Users owner) {
        this.text = text;
        this.tel = tel;
        this.date = date;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
