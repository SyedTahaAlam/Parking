package com.example.syedtahaalam.parkingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookParking extends Fragment {

    private Button first,second,third;

    public BookParking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_book_parking, container, false);
        first=(Button)mView.findViewById(R.id.parking_1);
        second=(Button)mView.findViewById(R.id.parking_2);
        third=(Button)mView.findViewById(R.id.parking_3);
        final Intent intent=new Intent(getContext(),BookingDetails.class);
            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            });

            second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.putExtra("type","2");
                    startActivity(intent);
                }
            });
            third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.putExtra("type","3");
                    startActivity(intent);
                }
            });

        return mView;
    }

}
