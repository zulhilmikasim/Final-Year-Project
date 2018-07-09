package com.test.user.schedule;

import android.content.Context;
import android.content.SharedPreferences;
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

public class FromScheduleActivity extends BaseActivity {

    private RecyclerView mProductReview;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Schedule> mProductList = new ArrayList<>();
    private FromScheduleAdapter mAdapter;
    TextView tv;
    SharedPreferences.Editor editor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_from_schedule, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("Select From Schedule");
        // now you can do all your other stuffs
        tv = (TextView)findViewById(R.id.tvError) ;

        String origin = getIntent().getStringExtra("Origin");
        String destination = getIntent().getStringExtra("Destination");
        String date = getIntent().getStringExtra("Date");
        String rdate = getIntent().getStringExtra("RDate");

        mProductReview = (RecyclerView)findViewById(R.id.product_rview);
        mLayoutManager = new LinearLayoutManager(this);
        mProductReview.setLayoutManager(mLayoutManager);
        mAdapter = new FromScheduleAdapter(this, mProductList);
        mProductReview.setAdapter(mAdapter);
        fetchdata(origin,destination,date);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        editor=pref.edit();


        editor.putString("org", origin);
        editor.putString("dest", destination);
        editor.putString("Rdate",rdate);
        editor.commit();

    }


    private void fetchdata(final String org, final String dest, String date) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("schedule");
        Query query = myRef.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    mProductList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        if(ds.getValue()!=null){
                            Schedule item = ds.getValue(Schedule.class);
                            if(item.getPickup().equals(org)){
                                if(item.getDestination().equals(dest)){
                                    mProductList.add(item);
                                }

                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
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
