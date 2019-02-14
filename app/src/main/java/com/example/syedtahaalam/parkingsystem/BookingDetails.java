package com.example.syedtahaalam.parkingsystem;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syedtahaalam.parkingsystem.Adapters.ParkingAdapter;
import com.example.syedtahaalam.parkingsystem.DbContract.Booking;
import com.example.syedtahaalam.parkingsystem.DbContract.ParkingDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.*;

public class BookingDetails extends AppCompatActivity  implements View.OnClickListener{

    private TextView mTimeFrom,mTimeTo;
    private Button mPickTimeFrom,mPickTimeTo,mBook;
    private Spinner mHours;
    private String hour,mDay,mYear,mMonth,mStartHour,mStartMinute;
    private List<String> mParkingDetails;
    private RecyclerView recyclerView;
    private ParkingAdapter adapters;
    private Intent intent;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReferenceForRecyclerView;
    private DatabaseReference mDatabaseReferenceParking;
    private FirebaseAuth mFirebaseAuth;
    private Booking booking;
    private boolean allowed;
    private ArrayList<ParkingDetails> parking;
    private Set<ParkingDetails> details;
    final ArrayList<Booking> listed =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        allowed=true;
        intent=getIntent();
        details=new HashSet<ParkingDetails>();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReferenceParking=mFirebaseDatabase.getReference("Parking");
        mDatabaseReferenceParking=mDatabaseReferenceParking.child("Place"+intent.getStringExtra("type"));
        mDatabaseReference=mFirebaseDatabase.getReference("Booking");
        mDatabaseReferenceForRecyclerView=mFirebaseDatabase.getReference("Booking");

        recyclerView = (RecyclerView) findViewById(R.id.places);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(BookingDetails.this,2);
        mParkingDetails=new ArrayList<>();
        adapters=new ParkingAdapter(mParkingDetails,BookingDetails.this);
//         recyce.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        attachListener();

        mTimeFrom=findViewById(R.id.time_from);
        mPickTimeFrom=findViewById(R.id.pick_time_from);
        mBook=findViewById(R.id.book);
        mHours=findViewById(R.id.hour);
        parking=new ArrayList<>();
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_SHORT).show();
                hour=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mHours.setAdapter(adapter);
//        Intent intent=getIntent();
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle(intent.getStringExtra("name"));
        mTimeFrom.setText(DateFormat.getDateTimeInstance().format(new Date()));


        mPickTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                String dateToUse=DateFormat.getDateInstance().format(new Date());
                DateTimePickerFragment datePickerFragment =
                        FragmentFactory.createDatePickerFragment(dateToUse, "The", DateTimePickerFragment.BOTH,
                                new DateTimePickerFragment.ResultHandler() {
                                    @Override
                                    public void setDate(Date result,int day,int month,int year,Integer hour,Integer minute) {
                                        Date date=result;
                                        updateDateTimeTextView(result,day,month,year,hour,minute);
                                    }
                                });
                datePickerFragment.show(fragmentManager, DateTimePickerFragment.DIALOG_TAG);
            }
        });
        mBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mParkingDetails.clear();
               Log.e("name",mDay);
                if (allowed)

                {
                    booking=new Booking(mFirebaseAuth.getUid(),getIntent().getStringExtra("type"),hour,mStartHour.toString(),mStartMinute.toString(),String.valueOf(valueOf(mStartHour)+ valueOf(hour)),mStartMinute.toString(),String.valueOf(mDay),String.valueOf(valueOf(mMonth)+1),String.valueOf(mYear),getIntent().getStringExtra("type"));

                    chkplace();

                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapters);
                }
            }
        });
        if (mParkingDetails.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.gone_layout).setVisibility(View.VISIBLE);
        }else{
            recyclerView.setAdapter(adapters);

        }

    }

    private void attachListener() {
        mDatabaseReferenceParking.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshots, @Nullable String s) {
                final ParkingDetails pk=dataSnapshots.getValue(ParkingDetails.class);
                Log.e("",pk.getname());
                parking.add(pk);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshots, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshots) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshots, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseErrors) {

            }
        });
//        Log.e("",String.valueOf(parking.size()));


        mDatabaseReferenceForRecyclerView.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getValue()!=null) {
                    if ((dataSnapshot.getValue(Booking.class)).getFloor().equals(getIntent().getStringExtra("type"))) {
                        Booking booking = dataSnapshot.getValue(Booking.class);
                        listed.add(booking);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void clicked(String name){
        Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
        booking.setArea(name);
        DatabaseReference db=mFirebaseDatabase.getReference("Booking");
        db.push().setValue(booking);
        mParkingDetails.clear();
        adapters.notifyDataSetChanged();
    }
    public void chkplace(){

        Set<String> bookings=new HashSet<>();
        Set<String> books=new HashSet<>();
        Set<String> history=new HashSet<>();
        ArrayList<Booking> no=new ArrayList<>();


        boolean  check=false;
        for (int j = 0; j< parking.size(); j++){
            ParkingDetails pk=parking.get(j);
            Booking catched=null;
          for (int i=0;i<listed.size();i++){
              Booking dataSet= listed.get(i);

              if(dataSet.getArea().equals(pk.getname())){
                  if(((dataSet.getDay().equals(booking.getDay()))&&(dataSet.getMonth().equals(booking.getMonth()))&&(dataSet.getYear().equals(booking.getYear())))) {
                       {
                            if((valueOf(dataSet.getStartHour())>= valueOf(booking.getStartHour())&& valueOf(dataSet.getEndHour())<= valueOf(booking.getEndHour()))){
                                if(valueOf(dataSet.getStartMin())>= valueOf(booking.getStartMin())&& valueOf(dataSet.getEndMin())<= valueOf(booking.getEndHour()))
                                    catched=dataSet;
                                break;
                            }else
                                if(valueOf(dataSet.getStartHour())<= valueOf(booking.getStartHour())&& valueOf(dataSet.getEndHour())>= valueOf(booking.getEndHour())){
                                    if(valueOf(dataSet.getStartMin())>= valueOf(booking.getStartMin())&& valueOf(dataSet.getEndMin())<= valueOf(booking.getEndHour()))

                                catched=dataSet;
                                   break;
                                }
                                else
                                if(valueOf(dataSet.getStartHour())<= valueOf(booking.getStartHour())&& valueOf(dataSet.getEndHour())>= valueOf(booking.getStartHour())){
                                    if(valueOf(dataSet.getStartMin())>= valueOf(booking.getStartMin())&& valueOf(dataSet.getEndMin())<= valueOf(booking.getEndHour()))

                                    catched=dataSet;
                                    break;
                                }
//                                else
//                                if(valueOf(dataSet.getStartHour())>= valueOf(booking.getStartHour())&& valueOf(dataSet.getEndHour())>= valueOf(booking.getStartHour())){
//                                catched=dataSet;
//
//                                    continue;
//                                }



                       }
                  }

          }
          }
          if (catched!=null) {
              if (!catched.getArea().equals(pk.getname()))
              {
                  if(!bookings.contains(catched))
                  bookings.add(pk.getname());
                  else
                      bookings.remove(catched);
              }
          }else{
              bookings.add(pk.getname());

          }

        }
//          if(!no.isEmpty())
//          {bookings.clear();
//            for (int i=0;i<no.size();i++)
//            {
//                if (!parking.contains(no.get(i).getArea()))
//                    bookings.add(no.get(i).getArea());
//            }}
        mParkingDetails.clear();
        mParkingDetails.addAll(bookings);
        adapters.notifyDataSetChanged();

           }

    private void removeElement(ParkingDetails p) {

    }

//    private int find(List<ParkingDetails> mParkingDetails) {
////           int i=0;
////
////            for (i=0;i<mParkingDetails.size();i++){
////                if(chkpresence(mParkingDetails.get(i).getname())){
////                    return i;
////                }
////        }
//        return -1;
//    }

    private boolean chkpresence(String area,List<String> str) {
        return str.contains(area);
    }


    private void insertData(final Booking booking) {
   mDatabaseReference.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(dataSnapshot.getValue()==null){
               mDatabaseReference=mDatabaseReference.child(mTimeFrom.getText().toString());

               mDatabaseReference.setValue(booking, new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                       Toast.makeText(BookingDetails.this, "data added", Toast.LENGTH_SHORT).show();
                   }
               });
               mDatabaseReference=mFirebaseDatabase.getReference("Booking");
           }
           else{

                for ( DataSnapshot snapshot:dataSnapshot.getChildren())
                {    Booking dataSet=snapshot.getValue(Booking.class);
                   if(dataSet.getArea().equals(getSupportActionBar().getTitle().toString())){
                       if(!((dataSet.getDay().equals(booking.getDay()))&&(dataSet.getMonth().equals(booking.getMonth()))&&(dataSet.getYear().equals(booking.getYear()))))
                           if(!(valueOf(dataSet.getStartHour())>= valueOf(booking.getStartHour())&& valueOf(dataSet.getEndHour())<= valueOf(booking.getEndHour())))
                           { mDatabaseReference=mDatabaseReference.child(mTimeFrom.getText().toString());

                               mDatabaseReference.setValue(booking, new DatabaseReference.CompletionListener() {
                                   @Override
                                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                       Toast.makeText(BookingDetails.this, "Booked", Toast.LENGTH_SHORT).show();
                                   }
                               });
                               mDatabaseReference=mFirebaseDatabase.getReference("Booking");

                           }
                           else{
                               Toast.makeText(BookingDetails.this, "This Time Is Booked", Toast.LENGTH_SHORT).show();
                           }
                   }

               }}

       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void updateDateTimeTextView(Date result, int day, int month, int year, Integer hour, Integer minute) {
      mTimeFrom.setText(result.toString());
       mYear=String.valueOf(year);
       mDay=String.valueOf(day);
       mMonth=String.valueOf(month);
       mStartMinute=String.valueOf(minute);
       mStartHour=String.valueOf(hour);

       if (!result.equals(new Date()))
           if(!result.after(new Date())){
               Toast.makeText(this, "Wrong date is selected", Toast.LENGTH_SHORT).show();
        allowed=false;
       }


    }

    @Override
    protected void onStop() {
        super.onStop();
        mParkingDetails.clear();
    }

    @Override
    public void onClick(View view) {

    }
}

