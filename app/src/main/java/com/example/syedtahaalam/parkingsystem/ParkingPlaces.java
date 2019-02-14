package com.example.syedtahaalam.parkingsystem;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class ParkingPlaces extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private String mName;
    private String mType;
    private Intent intent;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent=getIntent();
        mName=intent.getStringExtra("name");
        mType=intent.getStringExtra("type");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         mFirebaseAuth=FirebaseAuth.getInstance();
        name=navigationView.getHeaderView(0).findViewById(R.id.name);
        name.setText(mName);
        name.setAllCaps(true);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("User");


        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new viewParking()).commit();
        navigationView.setNavigationItemSelectedListener(this);
        if (mType.equals("user"))
        navigationView.inflateMenu(R.menu.activity_parking_places_drawer);
        else
            navigationView.inflateMenu(R.menu.admin_menu);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame,new viewParking()).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parking_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            mFirebaseAuth.signOut();
            Intent intent =new Intent(ParkingPlaces.this,SignIn.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager mFragementFragmentManager =getSupportFragmentManager();

        int id = item.getItemId();

        if (id == R.id.parking_place) {
            BookParking mParkingPlaces=new BookParking();
            mFragementFragmentManager.beginTransaction().replace(R.id.frame,mParkingPlaces).commit();

        } else if (id == R.id.view_user) {
            ViewUser user=new ViewUser();
            mFragementFragmentManager.beginTransaction().replace(R.id.frame,user).commit();

        } else if (id == R.id.cancel_parking) {
            cancelParking mCancelParking=new cancelParking();
            mFragementFragmentManager.beginTransaction().replace(R.id.frame,mCancelParking).commit();

        }  else if (id == R.id.feedback) {
            Feedback mFeedback=new Feedback();
            mFragementFragmentManager.beginTransaction().replace(R.id.frame,mFeedback).commit();
        }else if (id == R.id.reply_feedback) {
            FeedBackReply mFeedback=new FeedBackReply();
            mFragementFragmentManager.beginTransaction().replace(R.id.frame,mFeedback).commit();
        }
        else if (id == R.id.feedback_replies) {
            SeeReply seeReply=new SeeReply();
                mFragementFragmentManager.beginTransaction().replace(R.id.frame,seeReply).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void remove(final Booking model) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ParkingPlaces.this);
        dialog.setTitle("Cancel Parking");
        dialog.setMessage("Do You Want To Cancel Parking");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("Booking");
                mDatabaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Booking dataSet = dataSnapshot.getValue(Booking.class);
                        if (dataSet.getUID().equals(model.getUID()) && dataSet.getArea().equals(model.getArea()))
                            if (dataSet.getStartHour().equals(model.getStartHour()) && (dataSet.getDay().equals(model.getDay()) && dataSet.getMonth().equals(model.getMonth()) && dataSet.getYear().equals(model.getYear()))) {
                                mDatabaseReference.child(dataSnapshot.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(ParkingPlaces.this, "Parking Has Been Canceled", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = dialog.create();


        alertDialog.show();


    }
}
