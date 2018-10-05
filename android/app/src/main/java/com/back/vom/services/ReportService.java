package com.back.vom.services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.back.vom.models.Report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportService {


    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public void createReport(Report report, DatabaseReference.CompletionListener listener) {
        String uid = FirebaseAuth.getInstance().getUid();
        report.setCreatedBy(uid);
        report.setUid(reportsRef.push().getKey());
        reportsRef.child(report.getUid()).setValue(report,listener);
        usersRef.child(uid).child("reports").push().setValue(report.getUid());
    }

    public void getMyReports() {
        String uid  = FirebaseAuth.getInstance().getUid();
        usersRef.child(uid).child("reports").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void getAllReports(ValueEventListener listener) {
        reportsRef.addListenerForSingleValueEvent(listener);
    }



}
