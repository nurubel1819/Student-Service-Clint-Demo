package com.example.studentserviceclintdemo.model;

public class SingleResponseModel {
    private String message;

    public SingleResponseModel() {
    }

    public SingleResponseModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
