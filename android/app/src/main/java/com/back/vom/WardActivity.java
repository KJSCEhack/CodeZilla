package com.back.vom;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.back.vom.models.Ward;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WardActivity extends AppCompatActivity {

    public static Ward ward;


    @BindView(R.id.address_tv)
    TextView mAddressTV;

    @BindView(R.id.contacts_tv)
    Button mContactsTV;

    @BindView(R.id.name_tv)
    TextView mNameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward);

        ButterKnife.bind(this);



        if(ward == null)
            Toast.makeText(this, "No Data Avaialable", Toast.LENGTH_SHORT).show();
        else  {

            mAddressTV.setText("Address:"+ward.getAddress());
            mNameTV.setText("Name:"+ward.getName());
            mContactsTV.setText("Contacts: "+ward.getContacts().get(0));

        }

    }

    public void call(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ward.getContacts().get(0)));
        startActivity(intent);
    }
}
