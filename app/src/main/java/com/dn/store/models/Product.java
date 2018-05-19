package com.dn.store.models;

import java.util.Date;
import java.util.List;

public class Product {

    private String name;
    private List<String> details;
    private float price;
    private String image;
    private List<Category> categories;
    private float rating;
    private int ratingCount;
    private Long createdDate;

    public Product() {
    }

    public Product(String name, List<String> details, float price, String image, List<Category> categories, float rating, int ratingCount, Long createdDate) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.image = image;
        this.categories = categories;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
}

