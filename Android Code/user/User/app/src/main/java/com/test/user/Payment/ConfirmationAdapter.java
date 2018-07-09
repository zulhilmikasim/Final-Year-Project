package com.test.user.Payment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.user.Alarm.AlarmNotificationActivity;
import com.test.user.Booking.Booking;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.R;
import com.test.user.Receipt.ScheduleSummary;
import com.test.user.Ticket.ProfileActivity;

import java.util.ArrayList;

public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ScheduleSummary> confirmations;
    private ArrayList<Booking> bookings;
    private String payment;
    private DatabaseReference ticketlist, list;
    public String passname,num;
    private TextView username, phone;
    private ArrayList<UserInformation> userInfo = new ArrayList<>();
    private FirebaseAuth firebaseAuth;

    public ConfirmationAdapter(Context mContext, ArrayList<ScheduleSummary> confirmations,String payment) {
        this.mContext = mContext;
        this.confirmations = confirmations;
        this.payment = payment;


    }

    @Override
    public ConfirmationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation_model,null,false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ConfirmationAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return confirmations.size();
    }

    @Override
    public void onBindViewHolder(ConfirmationAdapter.MyViewHolder holder, int position) {
        holder.dateTxtView.setText(confirmations.get(position).getDate());
        holder.pickupTxtView.setText(confirmations.get(position).getPickup());
        holder.destinationTxtView.setText(confirmations.get(position).getDestination());
        holder.priceTxtView.setText(payment);
        holder.timeTxtView.setText(confirmations.get(position).getTime());
        holder.companyTxtView.setText(confirmations.get(position).getCompany());
        holder.nameTxtView.setText(confirmations.get(position).getName());
        holder.ageTxtView.setText(confirmations.get(position).getAge());
        holder.addressTxtView.setText(confirmations.get(position).getAddress());
        holder.regTxtView.setText(confirmations.get(position).getRegnum());
        holder.idtv.setText(confirmations.get(position).getId());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTxtView, pickupTxtView, destinationTxtView, priceTxtView, idtv,companyTxtView, timeTxtView, nameTxtView, ageTxtView, addressTxtView, regTxtView,usernameTxtView, contactTxtView;
        public Button btnFinish,btnAlarm;

        public MyViewHolder(final View itemView) {
            super(itemView);
            dateTxtView = (TextView) itemView.findViewById(R.id.date_txt);
            pickupTxtView = (TextView) itemView.findViewById(R.id.pickup_txt);
            destinationTxtView = (TextView) itemView.findViewById(R.id.destination_txt);
            priceTxtView = (TextView) itemView.findViewById(R.id.price_txt);
            timeTxtView = (TextView) itemView.findViewById(R.id.time_txt);
            companyTxtView = (TextView) itemView.findViewById(R.id.company_txt);
            nameTxtView = (TextView) itemView.findViewById(R.id.name_txt);
            ageTxtView = (TextView) itemView.findViewById(R.id.age_txt);
            addressTxtView = (TextView) itemView.findViewById(R.id.address_txt);
            regTxtView = (TextView) itemView.findViewById(R.id.reg_txt);
            idtv = (TextView) itemView.findViewById(R.id.bookid);
            btnFinish = (Button)itemView.findViewById(R.id.btnMenu);
            btnAlarm = (Button)itemView.findViewById(R.id.btnAlarm);

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
                    passname = item.getUsername();
                    num = item.getPhone();
                    username = (TextView)itemView.findViewById(R.id.passname_txt);
                    phone = (TextView)itemView.findViewById(R.id.phone_txt);
                    username.setText(passname);
                    phone.setText(num);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
}