package com.example.syedtahaalam.parkingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mSeatNo;
    private EditText mPassword;
    private EditText mPhoneNo;


    private String mStringName;
    private String mStringEmail;
    private String mStringSeatNo;
    private String mStringPassword;
    private String mStringPhoneNo;
    private Button mSignUp;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mEmail = (EditText) findViewById(R.id.email);
        mSeatNo = (EditText) findViewById(R.id.seatNo);
        mPassword = (EditText) findViewById(R.id.password);
        mPhoneNo = (EditText) findViewById(R.id.phoneNo);
        mName = (EditText) findViewById(R.id.name);
        mSignUp = (Button) findViewById(R.id.sign_up);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("User");
        mFirebaseAuth = FirebaseAuth.getInstance();


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    public void registerUser() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout);

        mStringName = mName.getText().toString().trim();
        mStringSeatNo = mSeatNo.getText().toString().trim();
        mStringEmail = mEmail.getText().toString().trim();
        mStringPassword = mPassword.getText().toString().trim();
        mStringPhoneNo = mPhoneNo.getText().toString().trim();
        boolean chk1 = false, chk2 = false, chk3 = false, chk4 = false, chk5 = false;
        if (mStringName.isEmpty()) {
            mName.setError("Enter your Name!");
            mName.requestFocus();


        } else {
            chk1 = !chk1;
        }
        if (mStringSeatNo.isEmpty()) {
            mSeatNo.setError("Enter your Seat No!");
            mSeatNo.requestFocus();


        } else
            chk2 = !chk2;
        if (mStringEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches()) {
            mEmail.setError("Enter your Email!");
            mEmail.requestFocus();


        } else
            chk3 = !chk3;
        if (mStringPassword.isEmpty() || mStringPassword.length() < 6) {
            mPassword.setError("Enter your Password!");
            mPassword.requestFocus();
            chk4 = false;

        } else {
            chk4 = !chk4;
        }
        if (mStringPhoneNo.isEmpty()) {
            mPhoneNo.setError("Enter your Phone No!");
            mPhoneNo.requestFocus();

        } else {
            chk5 = !chk5;
        }
        if (chk1 && chk2 && chk3 && chk4 && chk5)
            mFirebaseAuth.createUserWithEmailAndPassword(mStringEmail, mStringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mLinearLayout.setVisibility(View.GONE);
                        String id="j";
                        if (task.isSuccessful()) {
                            id=mFirebaseAuth.getUid();
                            User currentUser = new User(mStringName, mStringEmail, mStringSeatNo, mStringPassword, mStringPhoneNo);
                            mDatabaseReference = mDatabaseReference.child(id);
                            mDatabaseReference.setValue(currentUser);
                            Intent intent = new Intent(getApplicationContext(), ParkingPlaces.class);
                            intent.putExtra("name", currentUser.getmName());
                            intent.putExtra("type", currentUser.getType());

                            startActivity(
                                    intent
                            );
                            finish();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("error",e.getMessage());
                }
            });
    }
}
