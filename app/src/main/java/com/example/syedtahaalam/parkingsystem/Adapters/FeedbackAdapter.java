package com.example.syedtahaalam.parkingsystem.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.syedtahaalam.parkingsystem.DbContract.ReplyContract;
import com.example.syedtahaalam.parkingsystem.Feedback;
import com.example.syedtahaalam.parkingsystem.R;
import com.example.syedtahaalam.parkingsystem.ReplyActivity;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    List<ReplyContract> feedbacks;

    public FeedbackAdapter(List<ReplyContract> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mFeedback,mName;
        public MyViewHolder(View view){
            super(view);
            mFeedback=view.findViewById(R.id.feedback);
            mName=view.findViewById(R.id.name);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item_reply,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    ReplyContract model=this.feedbacks.get(position);
        holder.mFeedback.setText(model.getFeedback());
        holder.mName.setText(model.getReply());
    }

    @Override
    public int getItemCount() {
        return this.feedbacks.size();
    }

   }
