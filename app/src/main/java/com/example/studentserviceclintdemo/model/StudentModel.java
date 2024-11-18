package com.example.studentserviceclintdemo.model;

public class StudentModel {
    private Long id;
    private String name;
    private String phone;
    private int cls;
    private String location;
    private String gender;
    private String department;
    private String description;

    public StudentModel() {
    }

    public StudentModel(Long id, String name, String phone, int cls, String location, String gender, String department, String description) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cls = cls;
        this.location = location;
        this.gender = gender;
        this.department = department;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

