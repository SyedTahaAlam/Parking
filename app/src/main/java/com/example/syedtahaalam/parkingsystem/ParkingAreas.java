package com.example.syedtahaalam.parkingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.syedtahaalam.parkingsystem.Adapters.ParkingAdapter;
import com.example.syedtahaalam.parkingsystem.DbContract.ParkingDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParkingAreas extends AppCompatActivity {

    private List<ParkingDetails> mParkingDetails;
    private RecyclerView recyclerView;
    private ParkingAdapter mParkingAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseRecyclerOptions<ParkingDetails> mParkingDetailsFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_areas);
//        Intent intent = getIntent();
//        String no = intent.getStringExtra("type");
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference("Parking");
//        mParkingDetails = new ArrayList<>();
//     mParkingAdapter=new ParkingAdapter(mParkingDetails);
//
//
//        if (no.equals("1")) {
//            mDatabaseReference = mDatabaseReference.child("Place1");
//        } else if (no.equals("2")) {
//            mDatabaseReference = mDatabaseReference.child("Place2");
//
//        } else if (no.equals("3")) {
//            mDatabaseReference = mDatabaseReference.child("Place3");
//
//        }
//
////        mParkingDetailsFirebaseRecyclerOptions =
////                new FirebaseRecyclerOptions.Builder<ParkingDetails>()
////                        .setQuery(mDatabaseReference, ParkingDetails.class)
////                        .build();
//
//
//        recyclerView = (RecyclerView) findViewById(R.id.book_parking_recyclerview);
//
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//            mDatabaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
//                    {
//                        ParkingDetails detail=snapshot.getValue(ParkingDetails.class);
//                        mParkingAdapter.add(detail);
//                        mParkingAdapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//            recyclerView.setAdapter(mParkingAdapter);
//        //        adapter = new FirebaseRecyclerAdapter<ParkingDetails, viewHolder>(mParkingDetailsFirebaseRecyclerOptions) {
////            TextView mStatus;
////            TextView mName;
////            Button mPark;
////            @Override
////            public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////                // Create a new instance of the ViewHolder, in this case we are using a custom
////                // layout called R.layout.message for each item
////                View view = LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.parking_item, parent, false);
////                mName=(TextView)view.findViewById(R.id.name);
////                mStatus=(TextView)view.findViewById(R.id.status);
////                mPark=(Button)view.findViewById(R.id.park);
////                return new viewHolder(view);
////            }
////
////            @Override
////            protected void onBindViewHolder(viewHolder holder, int position, final ParkingDetails model) {
////
////                mName.setText(model.getname());
////                mStatus.setText(model.getstatus());
////                Log.e("name", mName.getText().toString());
////
////                holder.mView.findViewById(R.id.park).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Toast.makeText(ParkingAreas.this, view.getId()+""+R.id.park, Toast.LENGTH_SHORT).show();
////                        if (view.getId()==R.id.park) {
////                            Intent intent = new Intent(getApplicationContext(), BookingDetails.class);
////                            intent.putExtra("name",model.getname());
////                            startActivity(intent);
////                        }
////                    }
////                });
////            }
////
////            @NonNull
////            @Override
////            public ParkingDetails getItem(int position) {
////                return super.getItem(position);
////            }
////        };
////        recyclerView.setAdapter(adapter);
////
//
    }


    private void attachListener(ParkingDetails parking) {
        mParkingDetails.add(parking);
       mParkingAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onStart() {
        super.onStart();
//            this.adapter.startListening();
        }

    @Override
    protected void onStop() {
        super.onStop();
//    this.adapter.stopListening();
    }
}
