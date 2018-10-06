package com.back.vom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.back.vom.models.Report;
import com.back.vom.models.User;
import com.back.vom.services.ReportService;
import com.back.vom.services.VolunteerListService;

import java.util.ArrayList;
import java.util.List;

public class VolunteersActivity extends AppCompatActivity {

    private TextView volunteersTextView;
    public static Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteers);


        volunteersTextView = findViewById(R.id.volunteers_tv);
        VolunteerListService.getVolunteers(report, new ReportService.CompleteListener() {
            @Override
            public void complete(Object object) {
                List<User> users = (List<User>) object;
                for(int i=0;i<users.size();i++) {
                    volunteersTextView.append(users.get(i)+"\n" );
                }
            }
        });


    }
}
