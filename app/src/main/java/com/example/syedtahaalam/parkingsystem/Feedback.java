package com.example.syedtahaalam.parkingsystem;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.UserFeedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {
        private EditText mFeedBack;
        private Button mSend;


        private FirebaseAuth mFirebaseAuth;
        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mDatabaseReference;



    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.fragment_feedback, container, false);;
        mFeedBack=(EditText)mView.findViewById(R.id.user_feedback);
        mSend=(Button)mView.findViewById(R.id.user_send);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Feedback");
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFeedback mUserFeedback=new UserFeedback(mFeedBack.getText().toString().trim(),mFirebaseAuth.getUid());

                mDatabaseReference.push().setValue(mUserFeedback, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(getContext(), "Feedback Submitted", Toast.LENGTH_SHORT).show();

                    }
                });
            mDatabaseReference=mFirebaseDatabase.getReference("Feedback");


         }
        });
        return mView;
    }

}
