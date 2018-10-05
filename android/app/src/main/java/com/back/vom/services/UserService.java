package com.back.vom.services;

import com.back.vom.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserService  {

    static DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
    static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");




    public static void createUser(FirebaseUser user, String name, String phone, String email, DatabaseReference.CompletionListener listener){
        usersRef
                .child(user.getUid())
                .setValue(new User(name,email,phone,user.getUid()),listener);
    }





}
