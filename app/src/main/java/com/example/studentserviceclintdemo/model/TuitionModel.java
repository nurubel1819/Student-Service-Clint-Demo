package com.example.studentserviceclintdemo.model;

public class TuitionModel {
    private Long id;
    private String category;
    private String institute;
    private String version;
    private String name;
    private String phone;
    private String department;
    private String grp;
    private String location;
    private String gender;
    private int cls;
    private double fee;
    private String description;

    public TuitionModel() {
    }

    public TuitionModel(Long id, String category, String institute, String version, String name, String phone, String department, String grp, String location, String gender, int cls, double fee, String description) {
        this.id = id;
        this.category = category;
        this.institute = institute;
        this.version = version;
        this.name = name;
        this.phone = phone;
        this.department = department;
        this.grp = grp;
        this.location = location;
        this.gender = gender;
        this.cls = cls;
        this.fee = fee;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
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

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
