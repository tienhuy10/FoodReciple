package com.example.foodreciple.Model;

public class User {
    public int id;
    public String username;
    public String password;
    public String email;
    public int phanquyen;

    public User(int id, String username, String password, String email, int phanquyen) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phanquyen = phanquyen;
    }

}
