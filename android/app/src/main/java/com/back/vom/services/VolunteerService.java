package com.back.vom.services;

import android.content.Context;
import android.widget.Toast;
import com.back.vom.models.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerService {


    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    public static void addVolunteer(Report report, String userId, DatabaseReference.CompletionListener listener, Context mContext) {

        if (report.getVolunteer()) {
            report.getmVolunteers().add(userId);
            reportsRef.child(report.getUid()).setValue(report,listener);
        } else {

            Toast.makeText(mContext, "No Volunteers allowed for this report!", Toast.LENGTH_SHORT).show();
        }
    }
}
