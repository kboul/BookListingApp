package com.example.android.booklistingapp;

/**
 * Created by KostasBoul on 10.06.17.
 */

public class Book {

    private String image;
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;

    // Constructor
    public Book(String image, String title, String author, String publisher, String publishedDate) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }

    public Book(String title, String author, String publisher, String publishedDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
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

    public String getPublisher() { return publisher; }

    public String getPublishedDate() { return publishedDate; }
}
