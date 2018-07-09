package com.test.user.schedule;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.user.BaseActivity;
import com.test.user.R;

import java.util.ArrayList;

public class ReturnActivity extends BaseActivity {

    private RecyclerView mProductReview;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ReturnSchedule> mProductList = new ArrayList<>();
    private ReturnAdapter mAdapter;
    TextView tv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_return, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        // now you can do all your other stuffs

        ReturnSchedule model = new ReturnSchedule();
        model.setDate("3/7/2018");
        model.setPickup("Pahang");
        model.setDestination("Melaka");
        model.setPrice("RM 25.60");
        model.setTime("10:30 AM");
        model.setCompany("SuperNice");
        model.setName("Ali");
        model.setAge("43");
        model.setAddress("USM");
        model.setRegnum("PKF7573");

        String push = myRef.child("returnschedule").push().getKey();
        myRef.child("returnschedule").child(push).setValue(model);

        tv = (TextView)findViewById(R.id.tvError) ;

        mProductReview = (RecyclerView)findViewById(R.id.product_rview);
        mLayoutManager = new LinearLayoutManager(this);
        mProductReview.setLayoutManager(mLayoutManager);
        mAdapter = new ReturnAdapter(this, mProductList);
        mProductReview.setAdapter(mAdapter);
        fetchdata();
    }

    private void fetchdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("returnschedule");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    mProductList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ReturnSchedule item = ds.getValue(ReturnSchedule.class);
                        mProductList.add(item);
                        mAdapter.notifyDataSetChanged();
                    }

                }
                else{
                    tv.setText(R.string.notfound);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

