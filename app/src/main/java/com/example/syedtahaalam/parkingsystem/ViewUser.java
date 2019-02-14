package com.example.syedtahaalam.parkingsystem;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.syedtahaalam.parkingsystem.Adapters.UserAdapter;
import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.example.syedtahaalam.parkingsystem.DbContract.UserDetails;
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
public class ViewUser extends Fragment {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private List<UserDetails> mUsers;
    private UserAdapter adapter;
    public ViewUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View   view= inflater.inflate(R.layout.fragment_view_user, container, false);
        mFirebaseAuth=FirebaseAuth.getInstance();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("User");

        recyclerView=(RecyclerView)view.findViewById(R.id.view_user_recyclerview);
        mUsers=new ArrayList<>();
        adapter=new UserAdapter(mUsers);
        attach();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        if(mUsers.isEmpty()){
//            recyclerView.setVisibility(View.GONE);
//
//            view.findViewById(R.id.gone_layout).setVisibility(View.VISIBLE);
//
//        }
//        else
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void attach(){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    if (!snapshot.getKey().equals(FirebaseAuth.getInstance().getUid()))
                    {mUsers.add(new UserDetails(user,snapshot.getKey()));
                        Log.e("Booking",user.getmName()+""+user.getmEmail());
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


