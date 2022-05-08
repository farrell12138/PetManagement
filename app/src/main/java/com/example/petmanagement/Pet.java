package com.example.petmanagement;

import java.io.Serializable;

public class Pet implements Serializable {
    private int id;
    private int userId;
    private String name;
    private String sex;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;
    private String breed;
    private boolean fosterStatus;

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setFosterStatus(boolean fosterStatus) {
        this.fosterStatus = fosterStatus;
    }

    public Pet() {
    }

    public String isFoster() {
        return fosterStatus ? "寄养中" : "未寄养";
    }

    public Pet(int id, String name, boolean fosterStatus) {
        this.id = id;
        this.name = name;
        this.fosterStatus = fosterStatus;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
