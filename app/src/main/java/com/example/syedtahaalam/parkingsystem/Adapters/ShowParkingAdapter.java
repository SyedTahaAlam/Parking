package com.example.syedtahaalam.parkingsystem.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.R;

import java.util.List;

public class ShowParkingAdapter extends RecyclerView.Adapter<ShowParkingAdapter.MyViewHolder> implements View.OnClickListener {

    public List<Booking> mBookings;

    public ShowParkingAdapter(List<Booking> mBookings) {
        this.mBookings = mBookings;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mDate,mTime,mArea,mHour;
        public MyViewHolder(View view) {
            super(view );
            mDate=view.findViewById(R.id.date);
            mTime=view.findViewById(R.id.time);
            mArea=view.findViewById(R.id.area);
            mHour=view.findViewById(R.id.hour);
        }
    }

    public void add(Booking booking){
        this.mBookings.add(booking);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_parking_item,parent,false);

     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Booking model= this.mBookings.get(position);
        holder.mArea.setText(model.getArea());
        holder.mTime.setText(model.getStartHour()+ ":" + model.getStartMin() + ":00");
        holder.mDate.setText(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
        holder.mHour.setText(model.getHours());
    }

    @Override
    public int getItemCount() {

        return this.mBookings.size();
    }

    @Override
    public void onClick(View view) {

    }


}
