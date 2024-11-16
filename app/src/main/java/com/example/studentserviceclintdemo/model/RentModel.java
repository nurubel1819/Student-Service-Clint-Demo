package com.example.studentserviceclintdemo.model;

public class RentModel {
    private Long id;
    private String image;
    private String phone;
    private String location;
    private double price;
    private int floor;
    private int member;
    private String description;

    public RentModel() {
    }

    public RentModel(Long id, String image, String phone, String location, double price, int floor, int member, String description) {
        this.id = id;
        this.image = image;
        this.phone = phone;
        this.location = location;
        this.price = price;
        this.floor = floor;
        this.member = member;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
