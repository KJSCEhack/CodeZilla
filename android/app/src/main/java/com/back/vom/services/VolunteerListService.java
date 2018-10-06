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


    public static ArrayList<String> getVolunteers(Report report) {


        ArrayList<String> usersId = report.getVolunteers();
        final ArrayList<String> usernames = new ArrayList<String>();

        for(int i=0;i<usersId.size();i++) {

            usersRef.child(usersId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    usernames.add(user.getName());

                    /*GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String,User>>(){};
                    HashMap<String, User> userList = dataSnapshot.getValue(t);


                    Log.d(TAG, "onDataChange: Entries Exist");
                    if (userList != null) {

                        for (Map.Entry<String, User> entry : userList.entrySet()) {
                            String key = entry.getKey();
                            User value = entry.getValue();
                            usernames.add(value.getName());
                        }
                    }*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        return usernames;
    }
}
