package com.back.vom.services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.back.vom.R;
import com.back.vom.models.Report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.back.vom.models.Report.TAG;

public class ReportService {


    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public static void createReport(Report report) {
        String uid = FirebaseAuth.getInstance().getUid();
        report.setCreatedBy(uid);
        report.setUid(reportsRef.push().getKey());
        reportsRef.child(report.getUid()).setValue(report);
        usersRef.child(uid).child("reports").push().setValue(report.getUid());
    }

    public static void getMyReports(final CompleteListener listener) {
        final String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "getMyReports: "+uid);

        reportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Report>> t = new GenericTypeIndicator<HashMap<String,Report>>(){};
                HashMap<String,Report> reportList = dataSnapshot.getValue(t);
                List<Report> myList = new ArrayList<>();

                if(reportList == null) {
                    listener.complete(new Exception("Empty"));
                    return;
                }
                for(Map.Entry<String, Report> entry : reportList.entrySet()) {
                    String key = entry.getKey();
                    Report value = entry.getValue();
                    if(value.getCreatedBy().equals(uid))
                        myList.add(value);
                }

                listener.complete(myList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.complete(databaseError.toException());
            }
        });
    }

    public static void getAllReports(final CompleteListener listener) {
        reportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Report>> t = new GenericTypeIndicator<HashMap<String,Report>>(){};
                HashMap<String,Report> reportList = dataSnapshot.getValue(t);
                List<Report> myList = new ArrayList<>();

                if(reportList == null) {
                    listener.complete(new Exception("Empty"));
                    return;
                }
                Log.d(TAG, "onDataChange: Entries Exist");

                for(Map.Entry<String, Report> entry : reportList.entrySet()) {
                    String key = entry.getKey();
                    Report value = entry.getValue();
                    myList.add(value);
                }
                listener.complete(myList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.complete(databaseError.toException());
            }
        });
    }

    public static void getReport(String id, final CompleteListener listener) {
        reportsRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Report r = dataSnapshot.getValue(Report.class);
                listener.complete(r);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.complete(databaseError.toException());
            }
        });
    }

    public interface CompleteListener {
        public void complete(Object object);
    }



}
