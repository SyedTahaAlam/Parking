package com.example.syedtahaalam.parkingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private TextView mSignUp;
    private Button mSignIn;
    private EditText mPassword;
    private EditText mEmail;

    private String mStringEmail;
    private String mStringPassword;
    private String mUserID;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    public static SharedPreferences mSharedPrefrences;
    public static SharedPreferences.Editor mEditor;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("User");

        mSharedPrefrences=getPreferences(MODE_PRIVATE);
        mEditor=mSharedPrefrences.edit();
    mSignUp=(TextView)findViewById(R.id.register);
    mSignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(
                    new Intent(getApplicationContext(),
                            SignUp.class)
            );
            finish();
        }
    });
    mSignIn=(Button)findViewById(R.id.sign_in);
    mSignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginUser();
        }
    });
    }

    private void loginUser() {
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mLinearLayout=(LinearLayout)findViewById(R.id.layout);

        mStringEmail = mEmail.getText().toString().trim();
        mStringPassword = mPassword.getText().toString().trim();

        boolean chk1=false,chk2=false;


        if(mStringEmail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches()){
            mEmail.setError("Enter your Email!");
            mEmail.requestFocus();


        }else{
            chk1=!chk1;
        }
        if(mStringPassword.isEmpty()||mStringPassword.length()<6)
        {
            mPassword.setError("Enter your Password!");
            mPassword.requestFocus();
        }else{
            chk2=!chk2;
        }
        if(chk1&&chk2){
        mFirebaseAuth.signInWithEmailAndPassword(mStringEmail,mStringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                    attachListener();
                else{
                    Toast.makeText(SignIn.this, "No User Found", Toast.LENGTH_SHORT).show();
                }
//
//                startActivity(
//                        new Intent (
//                                getApplicationContext(),
//                                ParkingPlaces.class
//                        )
//                );
//                finish();
            }
        });}
    }

    private void attachListener() {



       mUserID=mFirebaseAuth.getUid();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                    if(snap.getKey().toString().equals(mUserID)) {

                        User currentUser = snap.getValue(User.class);

                        Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),ParkingPlaces.class);
                        intent.putExtra("name",currentUser.getmName());
                        intent.putExtra("type",currentUser.getType());
                        startActivity(intent);
                        finish();


                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
