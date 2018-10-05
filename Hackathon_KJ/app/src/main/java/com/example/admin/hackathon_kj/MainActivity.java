package com.example.admin.hackathon_kj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private LinearLayout registerLL;
    private AppCompatEditText nameEditText;
    private AppCompatEditText phoneEditText;
    private AppCompatEditText passwordEditText;
    private AppCompatEditText codeEditText;
    private AppCompatButton verifyButton;

    private String mVerificationCode;
    private String name, phone, password;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerLL = findViewById(R.id.register_ll);
        nameEditText = findViewById(R.id.name_et);
        phoneEditText = findViewById(R.id.phone_et);
        passwordEditText = findViewById(R.id.password_et);
        codeEditText = findViewById(R.id.code_et);
        codeEditText.setVisibility(View.GONE);
        verifyButton = findViewById(R.id.verify_bt);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                password = passwordEditText.getText().toString();
                Toast.makeText(MainActivity.this, "Code Sent!", Toast.LENGTH_SHORT).show();

                nameEditText.setVisibility(View.GONE);
                phoneEditText.setVisibility(View.GONE);
                codeEditText.setVisibility(View.VISIBLE);
                verifyButton.setText("Veirfy");
            }
        });

    }


    public void sendVerificationCode(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                codeEditText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(MainActivity.this, "Error while Processing!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationCode = s;
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationCode, code);

        signInUserWithPhoneAuth(credential);
    }

    private void signInUserWithPhoneAuth(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mUser = new User(name, phone, password);

                    mDatabase.child("users").child(phone).setValue(mUser);
                }
            }
        });
    }
}
