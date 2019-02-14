package com.example.syedtahaalam.parkingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.example.syedtahaalam.parkingsystem.DbContract.UserFeedback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackReply extends Fragment {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<UserFeedback> mParkingDetailsFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter adapter;


    public FeedBackReply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_feedback_reply, container, false);
        mFirebaseAuth=FirebaseAuth.getInstance();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Feedback");
        mParkingDetailsFirebaseRecyclerOptions=
                new FirebaseRecyclerOptions.Builder<UserFeedback>()
                        .setQuery(mDatabaseReference, UserFeedback.class)
                        .build();
        recyclerView=(RecyclerView)view.findViewById(R.id.feedback_reply_recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new FirebaseRecyclerAdapter<UserFeedback, viewHolder>(mParkingDetailsFirebaseRecyclerOptions) {
            TextView mFeedback,mName;
            @Override
            public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.feedback_item, parent, false);

                mFeedback=view.findViewById(R.id.feedback);
                mName=view.findViewById(R.id.name);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(viewHolder holder, int position, final UserFeedback model) {
                    mFeedback.setText(model.getmFeedback());
                    final String uid=model.getmName();

                    DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference("User");
                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap:dataSnapshot.getChildren())
                                if(snap.getKey().toString().equals(uid)) {

                                    User currentUser = snap.getValue(User.class);

                                    mName.setText(currentUser.getmName());

                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent=new Intent(getContext(),ReplyActivity.class);
                                intent.putExtra("feedback",model.getmFeedback());
                            intent.putExtra("name",model.getmName());

                            startActivity(intent);
                        }
                    });



            }

        };
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
