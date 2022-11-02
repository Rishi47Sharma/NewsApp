package com.example.newsapp.model;

public class News {
    public String title;



   public String author;
    public String url;
    public String urlImage;



    public News(String title, String author, String url, String urlImage) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.urlImage = urlImage;
    }
}
