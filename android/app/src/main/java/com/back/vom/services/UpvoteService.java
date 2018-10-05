package com.back.vom.services;

import com.back.vom.models.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpvoteService {


    DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public void upvote(Report report, DatabaseReference.CompletionListener listener) {

        report.setUpvotes(report.getUpvotes() + 1);
        reportsRef.child(report.getUid()).setValue(report,listener);
        //usersRef.child(uid).child("reports").push().setValue(report.getUid());
    }
}
