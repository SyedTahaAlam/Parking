package com.example.syedtahaalam.parkingsystem.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.User;
import com.example.syedtahaalam.parkingsystem.DbContract.UserDetails;
import com.example.syedtahaalam.parkingsystem.R;
import com.example.syedtahaalam.parkingsystem.Userdetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements View.OnClickListener {

    List<UserDetails> mUser;

    public UserAdapter(List<UserDetails> mUser) {
        this.mUser = mUser;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName,mStatus;
        LinearLayout layoout;
        public MyViewHolder(View view) {
            super(view);
            mName=view.findViewById(R.id.name);
            mStatus=view.findViewById(R.id.account_type);
            layoout=view.findViewById(R.id.layout);

        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final UserDetails model=this.mUser.get(position);

        holder.mName.setText(model.getmName());
        holder.mStatus.setText(model.getType());
        holder.layoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), Userdetails.class);
                intent.putExtra("ref",model.getReference());
                intent.putExtra("name",model.getmName());
                intent.putExtra("email",model.getmEmail());
                intent.putExtra("seatNo",model.getmSeatNo());
                intent.putExtra("type",model.getType());

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mUser.size();
    }

    @Override
    public void onClick(View view) {

    }



}
