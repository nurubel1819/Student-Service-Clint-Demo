package com.example.studentserviceclintdemo.model;

public class TuitionModel {
    private Long id;
    private String category;
    private String institute;
    private String version;
    private String name;
    private String phone;
    private String faculty;
    private String grp;
    private String location;
    private String gender;
    private int cls;
    private double fee;
    private String description;

    public TuitionModel() {
    }

    public TuitionModel(Long id, String category, String version, String institute, String name, String phone, String faculty, String grp, String location, int cls, String gender, double fee, String description) {
        this.id = id;
        this.category = category;
        this.version = version;
        this.institute = institute;
        this.name = name;
        this.phone = phone;
        this.faculty = faculty;
        this.grp = grp;
        this.location = location;
        this.cls = cls;
        this.gender = gender;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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
