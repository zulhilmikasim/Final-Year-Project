package com.test.user.Payment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.test.user.Alarm.AlarmNotificationActivity;
import com.test.user.BaseActivity;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.R;
import com.test.user.Receipt.ScheduleSummary;
import com.test.user.Seat.Seat;
import com.test.user.Seat.Seat2;
import com.test.user.Seat.Seat3;
import com.test.user.Ticket.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoundTripConfirmationActivity extends  BaseActivity {

    private RecyclerView rcw,rcw2;
    private LinearLayoutManager layoutManager,layoutManager2;
    private ArrayList<Confirmation> confirmations = new ArrayList<>();
    private ArrayList<ScheduleSummary> returnConfirmations = new ArrayList<>();
    private ArrayList<UserInformation> userInfo =new ArrayList<>();
    private ArrayList<Seat2> seatnum =new ArrayList<>();
    private ArrayList<Seat3> rseatnum =new ArrayList<>();
    private RoundTripConfirmationAdapter adapter;
    private ReturnTripAdapter retAdapter;
    private String seats, seats2;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_round_trip_confirmation, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("Ticket Summary");

        //Getting Intent
        Intent intent = getIntent();
        final String payment = intent.getStringExtra("PaymentAmount");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Seat");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                seatnum.clear();
                Seat2 item = dataSnapshot.getValue(Seat2.class);
                seatnum.add(item);
                seats = item.getSeatnum();
                init(payment,seats);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference myRef2 = database.getReference("User").child(user.getUid()).child("ReturnSeat");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rseatnum.clear();
                Seat3 item = dataSnapshot.getValue(Seat3.class);
                rseatnum.add(item);
                seats2 = item.getSeatnum();

                init2(payment,seats2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button btnFinish = (Button)findViewById(R.id.btnMenu);
        Button btnAlarm = (Button)findViewById(R.id.btnAlarm);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AlarmNotificationActivity.class);
                context.startActivity(intent);
            }
        });

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void init(String payment, String seats ){
        rcw = (RecyclerView)findViewById(R.id.fromrv);
        layoutManager = new LinearLayoutManager(this);
        rcw.setLayoutManager(layoutManager);
        adapter = new RoundTripConfirmationAdapter(this, confirmations,payment,seats);
        rcw.setAdapter(adapter);
        fetchdata();
        //fetchuser();

    }

    private void init2(String payment, String seats2){
        rcw2 = (RecyclerView)findViewById(R.id.torv);
        layoutManager2 = new LinearLayoutManager(this);
        rcw2.setLayoutManager(layoutManager2);
        retAdapter = new ReturnTripAdapter(this,returnConfirmations,payment,seats2);
        rcw2.setAdapter(retAdapter);
        fetchdata2();
        //fetchuser2();
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
                Confirmation item = dataSnapshot.getValue(Confirmation.class);
                confirmations.add(item);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchdata2() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("ReturnTicket");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                returnConfirmations.clear();
                ScheduleSummary item = dataSnapshot.getValue(ScheduleSummary.class);
                returnConfirmations.add(item);
                retAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   /* private void fetchuser(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Details");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo.clear();
                UserInformation item = dataSnapshot.getValue(UserInformation.class);
                userInfo.add(item);
                String passname = item.getUsername();
                String num = item.getPhone();
                username = (TextView)findViewById(R.id.passname_txt);
                phone = (TextView)findViewById(R.id.phone_txt);
                username.setText(passname);
                phone.setText(num);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
*/

   /* private void fetchuser2(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Details");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo.clear();
                UserInformation item = dataSnapshot.getValue(UserInformation.class);
                userInfo.add(item);
                String passname = item.getUsername();
                String num = item.getPhone();
                username = (TextView)findViewById(R.id.passname_txt);
                phone = (TextView)findViewById(R.id.phone_txt);
                username.setText(passname);
                phone.setText(num);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/


}

