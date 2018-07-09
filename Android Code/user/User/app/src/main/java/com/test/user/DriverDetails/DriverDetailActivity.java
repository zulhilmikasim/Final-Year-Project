package com.test.user.DriverDetails;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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
import com.test.user.Booking.Booking;
import com.test.user.R;
import com.test.user.schedule.Schedule;
import com.test.user.schedule.ScheduleAdapter;

import java.util.ArrayList;

public class DriverDetailActivity extends BaseActivity {

    private RecyclerView mProductReview, rcw;
    private LinearLayoutManager mLayoutManager,layoutManager;
    private ArrayList<DriverDetail> mProductList = new ArrayList<>();
    private ArrayList<DriverDetail> list = new ArrayList<>();
    private DriverDetailAdapter mAdapter;
    private ReturnDriverAdapter nAdapter;
    private FirebaseAuth firebaseAuth;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_driver_detail, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("View Driver Details");

        tvError = (TextView)findViewById(R.id.tvError);

        mProductReview = (RecyclerView)findViewById(R.id.detail);
        mLayoutManager = new LinearLayoutManager(this);
        mProductReview.setLayoutManager(mLayoutManager);
        mAdapter = new DriverDetailAdapter(this, mProductList);
        mProductReview.setAdapter(mAdapter);

        rcw = (RecyclerView)findViewById(R.id.retdetail);
        layoutManager = new LinearLayoutManager(this);
        rcw.setLayoutManager(layoutManager);
        nAdapter = new ReturnDriverAdapter(this, list);
        rcw.setAdapter(nAdapter);

        fetchdata();
        fetchdata2();

    }

    private void fetchdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Ticket");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    mProductList.clear();
                    DriverDetail item = dataSnapshot.getValue(DriverDetail.class);
                    mProductList.add(item);
                    mAdapter.notifyDataSetChanged();
                }
                else{

                    tvError.setText("There is no data available");
                }

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
                if(dataSnapshot.getValue()!=null){
                    list.clear();
                    DriverDetail item = dataSnapshot.getValue(DriverDetail.class);
                    list.add(item);
                    nAdapter.notifyDataSetChanged();
                }
                else{
                    tvError.setText(" ");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
