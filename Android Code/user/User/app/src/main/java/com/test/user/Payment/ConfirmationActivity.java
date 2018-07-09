package com.test.user.Payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.user.BaseActivity;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.R;
import com.test.user.Receipt.ScheduleSummary;
import com.test.user.Seat.Seat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfirmationActivity extends BaseActivity{

    private RecyclerView rcw;
    private LinearLayoutManager layoutManager;
    private ArrayList<ScheduleSummary> confirmations = new ArrayList<>();
    private ArrayList<Seat> seatnum =new ArrayList<>();
    private ArrayList<UserInformation> userInfo =new ArrayList<>();
    private ConfirmationAdapter adapter;
    public String seats,passname,num;
    private TextView SeatTxt,username, phone;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_confirmation, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("Ticket Summary");


        //Getting Intent
        Intent intent = getIntent();
        final String payment = intent.getStringExtra("PaymentAmount");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Seat");
       myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                seatnum.clear();
                Seat item = dataSnapshot.getValue(Seat.class);
                seatnum.add(item);
                seats = item.getSeatnum();
                SeatTxt = (TextView)findViewById(R.id.seat_txt);
                SeatTxt.setText(seats);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        init(payment);

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void init(String payment){

        rcw = (RecyclerView)findViewById(R.id.receiptRv);
        layoutManager = new LinearLayoutManager(this);
        rcw.setLayoutManager(layoutManager);
        adapter = new ConfirmationAdapter(this, confirmations, payment);
        rcw.setAdapter(adapter);
        fetchdata();

    }

    private void fetchdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Ticket");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmations.clear();
                ScheduleSummary item = dataSnapshot.getValue(ScheduleSummary.class);
                confirmations.add(item);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
