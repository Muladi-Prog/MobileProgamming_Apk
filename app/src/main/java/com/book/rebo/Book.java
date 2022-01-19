package com.book.rebo;

public class Book {
    private String image_url;
    private String name;
    private String description;
    private int isFavourite;

    public Book(String image_url, String name, String description, int isFavourite, String isi, String genre) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.isFavourite = isFavourite;
        this.isi = isi;
        this.genre = genre;
    }

    private String isi;
    private String genre;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book(){

    }




}
