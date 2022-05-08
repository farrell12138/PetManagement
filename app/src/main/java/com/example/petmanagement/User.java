package com.example.petmanagement;

public class User {
    private int id;
    private String userName;
    private String password;
    private String phone;
    private String address;

    public User() {
    }

    public User(int id, String userName, String password, String phone, String address) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
