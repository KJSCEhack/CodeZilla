package com.back.vom.services;

import com.back.vom.models.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpvoteService {


    static  DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public static void upvote(Report report, DatabaseReference.CompletionListener listener) {
        report.setUpvotes(report.getUpvotes() + 1);
        reportsRef.child(report.getUid()).setValue(report,listener);
    }
}
