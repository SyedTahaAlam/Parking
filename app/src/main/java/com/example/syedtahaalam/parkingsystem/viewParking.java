package com.example.syedtahaalam.parkingsystem;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.Adapters.ShowParkingAdapter;
import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.ParkingDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class viewParking extends Fragment {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private List<Booking> bookings;
    private ShowParkingAdapter adapter;
    public viewParking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view_parking, container, false);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Booking");
//        mParkingDetailsFirebaseRecyclerOptions=
//                new FirebaseRecyclerOptions.Builder<Booking>()
//                        .setQuery(mDatabaseReference, Booking.class)
//                        .build();
        recyclerView=(RecyclerView)view.findViewById(R.id.view_parking_recyclerview);
        bookings=new ArrayList<>();
          adapter=new ShowParkingAdapter(bookings);
        attach();
          RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        adapter = new FirebaseRecyclerAdapter<Booking, viewHolder>(mParkingDetailsFirebaseRecyclerOptions) {
//            TextView mDate,mTime,mArea,mHour;
//            ProgressBar progressBar;
//            @Override
//            public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.view_parking_item, parent, false);
//                mDate=view.findViewById(R.id.date);
//                mTime=view.findViewById(R.id.time);
//                mArea=view.findViewById(R.id.area);
//                mHour=view.findViewById(R.id.hour);
//                progressBar=view.findViewById(R.id.feed_loading);
//                return new viewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(viewHolder holder, int position, final Booking model) {
//                if (model.getUID().equals(FirebaseAuth.getInstance().getUid())) {
//                    mArea.setText(model.getArea());
//                    mTime.setText(model.getStartHour()+ ":" + model.getStartMin() + ":00");
//                    mDate.setText(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
//                    mHour.setText(model.getHours());
//                }
//
//            }
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
//
//        };
//
//        if (bookings.isEmpty()){
//            Log.e("no",String.valueOf(bookings.size()));
//            recyclerView.setVisibility(View.GONE);
//            view.findViewById(R.id.gone_layout).setVisibility(View.VISIBLE);
//        }else{
//            recyclerView.setAdapter(adapter);
//
//        }
        recyclerView.setAdapter(adapter);
        return view;
    }
    private  void attach(){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookings.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Booking booking=snapshot.getValue(Booking.class);
                    if (booking.getUID().equals(FirebaseAuth.getInstance().getUid())){
                        bookings.add(booking);
                        Log.e("Booking",booking.getArea()+""+booking.getDay());
                        adapter.notifyDataSetChanged();
                    }

                }
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
