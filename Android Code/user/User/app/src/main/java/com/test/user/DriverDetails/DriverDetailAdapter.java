package com.test.user.DriverDetails;

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
import com.test.user.Booking.Booking;
import com.test.user.R;
import com.test.user.Receipt.ScheduleSummary;
import com.test.user.Seat.SeatActivity;
import com.test.user.schedule.Schedule;

import java.util.ArrayList;

public class DriverDetailAdapter extends RecyclerView.Adapter<DriverDetailAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<DriverDetail> mProductList;


    public DriverDetailAdapter(Context mContext, ArrayList<DriverDetail> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public DriverDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driverdetail_model,null,false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new DriverDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DriverDetailAdapter.MyViewHolder holder, int position) {
        holder.companyTxtView.setText(mProductList.get(position).getCompany());
        holder.nameTxtView.setText(mProductList.get(position).getName());
        holder.ageTxtView.setText(mProductList.get(position).getAge());
        holder.addressTxtView.setText(mProductList.get(position).getAddress());
        holder.regTxtView.setText(mProductList.get(position).getRegnum());
        holder.idtv.setText(mProductList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView companyTxtView,nameTxtView, ageTxtView, addressTxtView, regTxtView, idtv;
        public Button btnNext;
        public MyViewHolder(View itemView) {
            super(itemView);
            companyTxtView = (TextView) itemView.findViewById(R.id.company_txt);
            nameTxtView = (TextView) itemView.findViewById(R.id.name_txt);
            ageTxtView = (TextView) itemView.findViewById(R.id.age_txt);
            addressTxtView = (TextView) itemView.findViewById(R.id.address_txt);
            regTxtView = (TextView) itemView.findViewById(R.id.reg_txt);
            idtv = (TextView) itemView.findViewById(R.id.bookid);
        }
    }
}