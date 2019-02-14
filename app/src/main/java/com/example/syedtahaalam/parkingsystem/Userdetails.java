package com.example.syedtahaalam.parkingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userdetails extends AppCompatActivity {
    private Intent intent;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener valueEventListener;
    private TextView name,email,seatNo,type;
    private Button remove;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        intent=getIntent();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("User");
        mDatabaseReference=mDatabaseReference.child(intent.getStringExtra("ref"));

        name =findViewById(R.id.name);
        email =findViewById(R.id.email);
        seatNo =findViewById(R.id.seatNo);
        type =findViewById(R.id.type);
        remove=findViewById(R.id.remove);


        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        seatNo.setText(intent.getStringExtra("seatNo"));
        type.setText(intent.getStringExtra("type"));
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Userdetails.this);
                dialog.setTitle("Cancel Parking");
                dialog.setMessage("Do You Want To Cancel Parking");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     mDatabaseReference.removeValue(new DatabaseReference.CompletionListener() {
                         @Override
                         public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                             Toast.makeText(Userdetails.this, "User Removed", Toast.LENGTH_SHORT).show();

                             finish();
                         }
                     });
                    }
                })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();

                            }
                        });

                AlertDialog alertDialog = dialog.create();


                alertDialog.show();

            }
        });



    }
}
