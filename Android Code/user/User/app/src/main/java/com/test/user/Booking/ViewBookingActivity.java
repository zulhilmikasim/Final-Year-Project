package com.test.user.Booking;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.test.user.DriverDetails.DriverDetail;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.Payment.ConfirmationAdapter;
import com.test.user.R;
import com.test.user.Receipt.ScheduleSummary;
import com.test.user.Seat.Seat;
import com.test.user.Seat.Seat2;
import com.test.user.Seat.Seat3;
import com.test.user.schedule.Schedule;

import java.util.ArrayList;

public class ViewBookingActivity extends BaseActivity implements NfcAdapter.CreateNdefMessageCallback{

    private RecyclerView rcw;
    private LinearLayoutManager layoutManager;
    private ArrayList<Booking> confirmations = new ArrayList<>();
    private BookingAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_view_booking, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("View Booking");

        Intent intent = getIntent();
        id = intent.getStringExtra("BookId");

        rcw = (RecyclerView)findViewById(R.id.bookingRv);
        layoutManager = new LinearLayoutManager(this);
        rcw.setLayoutManager(layoutManager);
        adapter = new BookingAdapter(this, confirmations);
        rcw.setAdapter(adapter);
        fetchdata();


        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);
    }

    private void fetchdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Booking");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    confirmations.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Booking item = ds.getValue(Booking.class);
                            confirmations.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        String message = id;
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}
