package com.back.vom.services;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.back.vom.models.Report;
import com.back.vom.models.User;
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

public class VolunteerListService {


    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public static void getVolunteers(Report report, final ReportService.CompleteListener listener) {


        final ArrayList<String> usersId = report.getVolunteers();
        final ArrayList<String> usernames = new ArrayList<String>();

        for(int i=0;i<usersId.size();i++) {

            usersRef.child(usersId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    usernames.add(user.getName());

                    if(usernames.size() == usersId.size()) {
                        listener.complete(usernames);
                        return;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
