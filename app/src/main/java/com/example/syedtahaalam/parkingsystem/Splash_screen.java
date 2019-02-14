package com.example.syedtahaalam.parkingsystem;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_screen extends AppCompatActivity {

    private FirebaseAuth mFirebaseauth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private String mUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("User");
        mFirebaseauth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFirebaseUser=mFirebaseauth.getCurrentUser();
                if(mFirebaseUser==null){
                    startActivity(new Intent(getApplicationContext(),SignIn.class));
                    finish();
                }
                else {
                    attachDatabaseReadListener();
                }


                }

        },4000);
    }

    private void attachDatabaseReadListener() {
        mUserID = mFirebaseauth.getUid();
        mDatabaseReference=mFirebaseDatabase.getReference("User");

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    for (com.google.firebase.database.DataSnapshot snap:snapshot.getChildren())
                    if(snap.getKey().toString().equals(mUserID)) {

                        User currentUser = snap.getValue(User.class);

                        Intent intent=new Intent(getApplicationContext(),ParkingPlaces.class);
                        intent.putExtra("name",currentUser.getmName());
                        intent.putExtra("type",currentUser.getType());
                        startActivity(intent);



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }


}
