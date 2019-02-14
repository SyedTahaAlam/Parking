package com.example.syedtahaalam.parkingsystem.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.CancelBooking;
import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.ParkingPlaces;
import com.example.syedtahaalam.parkingsystem.R;
import com.example.syedtahaalam.parkingsystem.cancelParking;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.MyViewHolder> {

    public List<Booking> mBookings;
    Context context;

    public CancelAdapter(List<Booking> mBookings,Context context) {
        this.mBookings = mBookings;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mDate,mTime,mArea,mHour;
        View mView;
        Button cancel;
        public MyViewHolder(View view) {
            super(view);
            mView=view;
            cancel=view.findViewById(R.id.cancel);
            mDate=view.findViewById(R.id.date);
            mTime=view.findViewById(R.id.time);
            mArea=view.findViewById(R.id.area);
            mHour=view.findViewById(R.id.hour);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancel_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Booking model=this.mBookings.get(position);


            holder.mArea.setText(model.getArea());
            holder.mTime.setText(model.getStartHour() + ":" + model.getStartMin() + ":00");
            holder.mDate.setText(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
            holder.mHour.setText(model.getHours());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ((ParkingPlaces)context).remove(model);
                    ((ParkingPlaces)context).remove(model);
                    Toast.makeText(context, "hello"+context, Toast.LENGTH_SHORT).show();
        }
    }
            );
    }

    private void remove(Booking model) {
        cancelParking cp=new cancelParking();
        cp.remove(model);
    }

    @Override
    public int getItemCount() {
        return this.mBookings.size();
    }
}
