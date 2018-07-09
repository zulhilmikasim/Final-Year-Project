package com.test.user.RoundTripReceipt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.user.R;
import com.test.user.Ticket.ReturnTicketInformation;

import java.util.ArrayList;

public class FromScheduleSummaryAdapter extends RecyclerView.Adapter<FromScheduleSummaryAdapter.MyViewHolder>{


    private Context mContext;
    private ArrayList<FromScheduleSummary> summary;
    private ArrayList<ReturnTicketInformation> passangers;

    public FromScheduleSummaryAdapter(Context mContext, ArrayList<FromScheduleSummary> summary, ArrayList<ReturnTicketInformation> passangers) {
        this.mContext = mContext;
        this.summary = summary;
        this.passangers = passangers;

    }

    @Override
    public FromScheduleSummaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_schedule,null,false);
        return new FromScheduleSummaryAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(FromScheduleSummaryAdapter.MyViewHolder holder, int position) {
        holder.dateTxtView.setText(summary.get(position).getDate());
        holder.pickupTxtView.setText(summary.get(position).getPickup());
        holder.destinationTxtView.setText(summary.get(position).getDestination());
        holder.priceTxtView.setText(summary.get(position).getPrice());
        holder.timeTxtView.setText(summary.get(position).getTime());
        holder.companyTxtView.setText(summary.get(position).getCompany());
        holder.nameTxtView.setText(summary.get(position).getName());
        holder.ageTxtView.setText(summary.get(position).getAge());
        holder.addressTxtView.setText(summary.get(position).getAddress());
        holder.regTxtView.setText(summary.get(position).getRegnum());
        holder.passTxtView.setText(passangers.get(position).getPassenger());
    }


    @Override
    public int getItemCount() {
        return summary.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView dateTxtView, pickupTxtView, destinationTxtView, priceTxtView, companyTxtView,timeTxtView, nameTxtView, ageTxtView, addressTxtView, regTxtView,passTxtView;
        public MyViewHolder(View itemView) {
            super(itemView);
            dateTxtView = (TextView) itemView.findViewById(R.id.date_txt);
            pickupTxtView = (TextView) itemView.findViewById(R.id.pickup_txt);
            destinationTxtView = (TextView) itemView.findViewById(R.id.destination_txt);
            priceTxtView = (TextView) itemView.findViewById(R.id.price_txt);
            timeTxtView = (TextView) itemView.findViewById(R.id.timetv);
            companyTxtView = (TextView) itemView.findViewById(R.id.companytv);
            nameTxtView = (TextView) itemView.findViewById(R.id.name_txt);
            ageTxtView = (TextView) itemView.findViewById(R.id.age_txt);
            addressTxtView = (TextView) itemView.findViewById(R.id.address_txt);
            regTxtView = (TextView) itemView.findViewById(R.id.reg_txt);
            passTxtView = (TextView) itemView.findViewById(R.id.pass_txt);

        }
    }
}
