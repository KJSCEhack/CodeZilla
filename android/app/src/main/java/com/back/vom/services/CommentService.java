package com.back.vom.services;

import com.back.vom.models.Comment;
import com.back.vom.models.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentService {


    DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public void addComment(Report report, Comment comment, DatabaseReference.CompletionListener listener) {

        report.mComments.add(comment);
        reportsRef.child(report.getUid()).setValue(report,listener);
        //usersRef.child(uid).child("reports").push().setValue(report.getUid());
    }
}
