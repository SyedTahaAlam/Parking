package com.example.syedtahaalam.parkingsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.syedtahaalam.parkingsystem.BookingDetails;
import com.example.syedtahaalam.parkingsystem.DbContract.ParkingDetails;
import com.example.syedtahaalam.parkingsystem.R;

import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.MyViewHolder> {
    List<String> parkingDetails;
    Context conxt;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mName,mStatus;
        public LinearLayout layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mName=(TextView)itemView.findViewById(R.id.name);
            mStatus=(TextView)itemView.findViewById(R.id.status);
            layout=itemView.findViewById(R.id.place);

        }
    }

    public ParkingAdapter(List<String> parkingDetails, Context context) {
        this.parkingDetails = parkingDetails;
        conxt=context;
    }
    public void add(String detail){
        this.parkingDetails.add(detail);
        this.notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final String mParkingPlace = parkingDetails.get(position);
        holder.mName.setText(mParkingPlace);
        holder.mStatus.setText("free");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BookingDetails)conxt).clicked(mParkingPlace);
            }
        });


    }

    @Override
    public int getItemCount() {

        return this.parkingDetails.size();
    }



    }
