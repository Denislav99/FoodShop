package com.example.foodshop.Model;

import java.util.Objects;

public class Grocery {
    public Grocery(int id, String itemID, String name, String price, String category, String description, String date) {
        this.id = id;
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public Grocery() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "id=" + id +
                ", itemID='" + itemID + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    private int id;
    private String itemID;
    private String name;
    private String price;
    private String category;
    private String description;
    private String date;
}
