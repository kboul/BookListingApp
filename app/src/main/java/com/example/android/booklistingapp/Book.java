package com.example.android.booklistingapp;

/**
 * Created by KostasBoul on 10.06.17.
 */

public class Book {

    private String image;
    private String title;
    private String author;

    // Constructor
    public Book(String image, String title, String author) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
