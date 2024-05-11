package com.example.foodreciple.Model;

public class Category {
    private int ID;
    private String CategoryName;

    public Category(int ID, String categoryName) {
        this.ID = ID;
        CategoryName = categoryName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
