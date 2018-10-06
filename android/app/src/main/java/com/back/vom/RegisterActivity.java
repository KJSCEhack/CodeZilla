package com.back.vom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.back.vom.services.SFHandler;
import com.back.vom.services.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.phone_et)
    EditText mPhoneET;

    @BindView(R.id.email_et)
    EditText mEmailET;

    @BindView(R.id.name_et)
    EditText mNameET;



    public static final String TAG = RegisterActivity.class.getSimpleName();


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void submit(View v ) {
        String email = mEmailET.getText().toString();
        String name = mNameET.getText().toString();
        String phone = mPhoneET.getText().toString();

        if(isValidEmail(email) && phone.length() == 10) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91"+ mPhoneET.getText().toString() ,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks);
        } else {
            Toast.makeText(this, "Enter Valid info", Toast.LENGTH_SHORT).show();
        }
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential);
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: ",e);
            Toast.makeText(RegisterActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String verificationId,
                PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            changeUsersDetails(user);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void changeUsersDetails(FirebaseUser user) {
        String email = mEmailET.getText().toString();
        final String name = mNameET.getText().toString();
        String phone = mPhoneET.getText().toString();
        UserService.createUser(user, name, phone, email, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                Intent i  = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                SFHandler.save(RegisterActivity.this,"name",name);
                finish();
            }
        });
    }

}
