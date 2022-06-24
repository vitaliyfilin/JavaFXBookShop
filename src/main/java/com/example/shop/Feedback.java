package com.example.shop;

public class Feedback {
    private Integer id;
    private Integer user_id;
    private String message;

    public Feedback(Integer id, Integer user_id, String message) {
        this.id = id;
        this.user_id = user_id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
