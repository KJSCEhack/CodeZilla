package com.back.vom.services;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.back.vom.models.Report;
import com.back.vom.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VolunteerListService {


    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


    public static ArrayList<String> getVolunteers(Report report) {


        ArrayList<String> usersId = report.getVolunteers();
        final ArrayList<String> usernames = new ArrayList<String>();

        for(int i=0;i<usersId.size();i++) {

            usersRef.child(usersId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = (User) dataSnapshot.getValue();
                    usernames.add(user.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        return usernames;
    }
}
