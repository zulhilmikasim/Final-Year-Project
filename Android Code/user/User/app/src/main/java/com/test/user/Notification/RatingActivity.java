package com.test.user.Notification;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.user.Booking.Booking;
import com.test.user.Booking.ViewBookingActivity;
import com.test.user.R;
import com.test.user.Ticket.ProfileActivity;

import java.util.ArrayList;

public class RatingActivity extends AppCompatActivity {
    public RatingBar ratingBar;
    public EditText edComment;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private ArrayList<Booking> summary = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private String name, pickup, dest, date, price, time, regnum;
    private TextView org, destiny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        edComment = (EditText)findViewById(R.id.edComment);
        org = (TextView)findViewById(R.id.orgTv);
        destiny = (TextView)findViewById(R.id.destTv);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Ticket");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() != null){
                    summary.clear();

                    Booking item = dataSnapshot.getValue(Booking.class);
                    pickup = item.getPickup();
                    dest = item.getDestination();
                    regnum = item.getRegnum();
                    org.setText(pickup);
                    destiny.setText(dest);

                }

                else{
                    Toast.makeText(getApplicationContext(), "No data Found", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RatingActivity.this, ProfileActivity.class));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Display rating by calling getRating() method.
     * @param view
     */
    public void rateMe(View view){
        Log.d("Regnum", regnum);
        Rating model = new Rating();
        model.setRate(String.valueOf(ratingBar.getRating()));
        model.setComment(edComment.getText().toString());
        model.setReg(regnum);

        String push = myRef.child("Rating").push().getKey();
        myRef.child("Rating").child(push).setValue(model);

        Toast.makeText(getApplicationContext(), "Thanks For Your Rating!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ViewBookingActivity.class));
    }
}
