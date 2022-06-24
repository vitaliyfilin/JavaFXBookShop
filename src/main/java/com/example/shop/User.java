package com.example.shop;

import com.example.shop.DataBase.Gender;

import java.math.BigDecimal;

public class User {
    private Gender gender;
    public int id;
    public String name;
    public String password;
    public String country;
    public BigDecimal money;
    public String role;

    public User(int id, String name, String password, Gender gender, String country, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.money = money;
    }

    public User(String name, String password, Gender gender, String country) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.country = country;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
