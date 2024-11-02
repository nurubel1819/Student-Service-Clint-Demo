package com.example.studentserviceclintdemo.model;

public class UserRegistrationModel {
    private String name;
    private String phone;
    private String password;
    private String confirm_password;

    public UserRegistrationModel() {
    }

    public UserRegistrationModel(String name, String confirm_password, String phone, String password) {
        this.name = name;
        this.confirm_password = confirm_password;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
