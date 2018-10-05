package com.back.vom.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User {



    String name;
    String emai;
    String phone;
    String uid;


    public User(String name, String emai, String phone, String uid) {
        this.name = name;
        this.emai = emai;
        this.phone = phone;
        this.uid = uid;
        this.reportsList = new ArrayList<>();
    }

    List<String> reportsList;

    User() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getReportsList() {
        return reportsList;
    }

    public void setReportsList(List<String> reportsList) {
        this.reportsList = reportsList;
    }
}