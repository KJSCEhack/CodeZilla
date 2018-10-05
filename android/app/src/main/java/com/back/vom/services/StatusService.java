package com.back.vom.services;

import com.back.vom.models.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusService {


    DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public void changeStatus(Report report, String status, DatabaseReference.CompletionListener listener) {

        report.setmStatus(status);
        reportsRef.child(report.getUid()).setValue(report,listener);
        //usersRef.child(uid).child("reports").push().setValue(report.getUid());
    }
}
