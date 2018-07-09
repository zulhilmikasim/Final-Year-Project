package com.test.user.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.user.R;
import com.test.user.Seat.SeatActivity;

import java.util.ArrayList;

public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<ReturnSchedule> mProductList;
    private DatabaseReference db;
    private DatabaseReference ticketlist, list, addlist;
    private FirebaseAuth firebaseAuth;

    public ReturnAdapter(Context mContext, ArrayList<ReturnSchedule> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public ReturnAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_model,null,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.dateTxtView.setText(mProductList.get(position).getDate());
        holder.pickupTxtView.setText(mProductList.get(position).getPickup());
        holder.destinationTxtView.setText(mProductList.get(position).getDestination());
        holder.priceTxtView.setText(mProductList.get(position).getPrice());
        holder.timeTxtView.setText(mProductList.get(position).getTime());
        holder.companyTxtView.setText(mProductList.get(position).getCompany());
        holder.nameTxtView.setText(mProductList.get(position).getName());
        holder.ageTxtView.setText(mProductList.get(position).getAge());
        holder.addressTxtView.setText(mProductList.get(position).getAddress());
        holder.regTxtView.setText(mProductList.get(position).getRegnum());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView dateTxtView,rdateTxtView, pickupTxtView, destinationTxtView, priceTxtView, companyTxtView,timeTxtView, nameTxtView, ageTxtView, addressTxtView, regTxtView;
        public Button btnNext;
        public MyViewHolder(View itemView) {
            super(itemView);
            dateTxtView = (TextView)itemView.findViewById(R.id.date_txt);
            pickupTxtView = (TextView)itemView.findViewById(R.id.pickup_txt);
            destinationTxtView = (TextView)itemView.findViewById(R.id.destination_txt);
            priceTxtView = (TextView)itemView.findViewById(R.id.price_txt);
            timeTxtView = (TextView)itemView.findViewById(R.id.time_txt);
            companyTxtView = (TextView)itemView.findViewById(R.id.company_txt);
            nameTxtView = (TextView)itemView.findViewById(R.id.name_txt);
            ageTxtView = (TextView)itemView.findViewById(R.id.age_txt);
            addressTxtView = (TextView)itemView.findViewById(R.id.address_txt);
            regTxtView = (TextView)itemView.findViewById(R.id.reg_txt);
            btnNext = (Button)itemView.findViewById(R.id.btnNext);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String date = dateTxtView.getText().toString().trim();
                    String pickup = pickupTxtView.getText().toString().trim();
                    String destination = destinationTxtView.getText().toString().trim();
                    String price = priceTxtView.getText().toString().trim();
                    String time = timeTxtView.getText().toString().trim();
                    String company = companyTxtView.getText().toString().trim();
                    String name = nameTxtView.getText().toString().trim();
                    String age = ageTxtView.getText().toString().trim();
                    String address = addressTxtView.getText().toString().trim();
                    String regnum = regTxtView.getText().toString().trim();

                    ReturnSchedule schedule = new ReturnSchedule(date,pickup,destination,price,company,time,name,age,address,regnum);

                    db = FirebaseDatabase.getInstance().getReference();
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    ticketlist = db.child("User");
                    list = ticketlist.child(user.getUid());
                    addlist = list.child("Ticket");
                    addlist.setValue(schedule);

                    Context context = v.getContext();
                    Intent intent = new Intent(context, SeatActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
