package com.example.admin.hackathon_kj;

public class User {

    String name;
    String phoneNo;
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String phoneNo, String password) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
    }
}
