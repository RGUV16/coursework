/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class Article {
    private int no;
    private String category;
    private String title;
    private String content;
    private String author;
    private Date date;

    public Article(int no, String category, String title, String content, String author, Date date) {
        this.no = no;
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }
    
    public int getNo() {
        return no;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
    
    public Date getDate() {
        return date;
    }
}
