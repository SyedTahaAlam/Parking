package com.example.syedtahaalam.parkingsystem;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.Adapters.CancelAdapter;
import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.ParkingDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class cancelParking extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;

    private List<Booking> parkings;
    private CancelAdapter mCancelAdapter;

    public cancelParking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cancel_parking, container, false);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Booking");
        parkings=new ArrayList<>();
        mCancelAdapter=new CancelAdapter(parkings,getContext());
        attach();
//        mParkingDetailsFirebaseRecyclerOptions=
//                new FirebaseRecyclerOptions.Builder<Booking>()
//                        .setQuery(mDatabaseReference, Booking.class)
//                        .build();
        recyclerView=(RecyclerView)view.findViewById(R.id.cancel_booking_recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter = new FirebaseRecyclerAdapter<Booking, viewHolder>(mParkingDetailsFirebaseRecyclerOptions) {
//           TextView mDate,mTime,mArea,mHour;
//            ProgressBar progressBar;
//            @Override
//            public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.cancel_item, parent, false);
//                mDate=view.findViewById(R.id.date);
//                mTime=view.findViewById(R.id.time);
//                mArea=view.findViewById(R.id.area);
//                mHour=view.findViewById(R.id.hour);
//
//                return new viewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(viewHolder holder, int position, final Booking model) {
//                if (model.getUID().equals(FirebaseAuth.getInstance().getUid())) {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyyy");
//                    Date date = null;
//                    Date current=null;
//                    try {
//                        date = dateFormat.parse(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    if (new Date().after(date)) {
//
//
//                        mArea.setText(model.getArea());
//                        mTime.setText(model.getStartHour() + ":" + model.getStartMin() + ":00");
//                        mDate.setText(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
//                        mHour.setText(model.getHours());
//                        holder.mView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
//                                dialog.setTitle("Cancel Parking");
//                                dialog.setMessage("Do You Want To Cancel Parking");
//                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        final DatabaseReference mDatabaseReference=mFirebaseDatabase.getReference("Booking");
//                                        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//                                            @Override
//                                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                                                Booking dataSet=dataSnapshot.getValue(Booking.class);
//                                                if(dataSet.getUID().equals(model.getUID())&&dataSet.getArea().equals(model.getArea()))
//                                                if(dataSet.getStartHour().equals(model.getStartHour())&&(dataSet.getDay().equals(model.getDay())&&dataSet.getMonth().equals(model.getMonth())&&dataSet.getYear().equals(model.getYear())))
//                                                {
//                                                    mDatabaseReference.child(dataSnapshot.getKey()).removeValue(new DatabaseReference.CompletionListener() {
//                                                        @Override
//                                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                                                            Toast.makeText(getContext(), "Parking Has Been Canceled", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                            }
//
//                                            @Override
//                                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                            }
//
//                                            @Override
//                                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//                                    }
//                                })
//                                 .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                                     public void onClick(DialogInterface dialog,int id) {
//                                         // if this button is clicked, just close
//                                         // the dialog box and do nothing
//                                         dialog.cancel();
//
//                                     }
//                                 });
//
//                                AlertDialog alertDialog = dialog.create();
//
//
//                                alertDialog.show();
//
//                            }
//                        });
//                    }
//                }
//            }
//
//
//            @Override
//            public int getItemCount() {
//                return super.getItemCount();
//            }
//
//            @Override
//            public void onDataChanged() {
//                super.onDataChanged();
//                if(this.getItemCount()==0)
//                    progressBar.setVisibility(View.VISIBLE);
//
//
//            }
//        };

        recyclerView.setAdapter(mCancelAdapter);


        return view;
    }

    public void remove(final Booking model) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
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
                                        Toast.makeText(getContext(), "Parking Has Been Canceled", Toast.LENGTH_SHORT).show();
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
    private void attach() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {       Booking model=snapshot.getValue(Booking.class);
                    if (model.getUID().equals(FirebaseAuth.getInstance().getUid())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyyy");
                    Date date = null;
                    Date current=null;
                    try {
                        date = dateFormat.parse(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (new Date().after(date)) {
                        parkings.add(model);
                        mCancelAdapter.notifyDataSetChanged();
                    }
                }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
