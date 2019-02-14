package com.example.syedtahaalam.parkingsystem;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.ReplyContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReplyActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private TextView mFeedback;
    private EditText mReply;

    private Button mSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Reply");

        mSend=findViewById(R.id.feedback_send);
        mReply=findViewById(R.id.reply);
        mFeedback=findViewById(R.id.feedback);
        mFeedback.setText(getIntent().getStringExtra("feedback"));

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplyContract reply=new ReplyContract(mReply.getText().toString(),mFeedback.getText().toString(),getIntent().getStringExtra("name"));
                DatabaseReference ref=mDatabaseReference.child(reply.getUID());
                ref.setValue(reply, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(ReplyActivity.this, "Reply Send", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });



    }
}
