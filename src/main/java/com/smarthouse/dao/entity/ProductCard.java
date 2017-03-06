package com.smarthouse.dao.entity;

import javax.persistence.*;

@Entity
public class ProductCard {

    @Id
    private String sku;

    private String name;

    private int price;

    private int amount;

    private int likes;

    private int dislikes;

    private String productdescription;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category")
    Category category;

    public ProductCard(String sku, String name, int price, int amount, int likes, int dislikes, String productdescription, Category category) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.likes = likes;
        this.dislikes = dislikes;
        this.productdescription = productdescription;
        this.category = category;
    }

    public ProductCard() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getProductDescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
