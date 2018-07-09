package com.test.user.Booking;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.test.user.R;

import java.util.ArrayList;

public class ReturnBookingAdapter extends RecyclerView.Adapter<ReturnBookingAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Booking> confirmations;
    private String seatnum;

    public ReturnBookingAdapter(Context mContext, ArrayList<Booking> confirmations, String seatnum) {
        this.mContext = mContext;
        this.confirmations = confirmations;
        this.seatnum = seatnum;


    }

    @Override
    public ReturnBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_model,null,false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ReturnBookingAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return confirmations.size();
    }

    @Override
    public void onBindViewHolder(ReturnBookingAdapter.MyViewHolder holder, int position) {
        holder.dateTxtView.setText(confirmations.get(position).getDate());
        holder.pickupTxtView.setText(confirmations.get(position).getPickup());
        holder.destinationTxtView.setText(confirmations.get(position).getDestination());
        holder.timeTxtView.setText(confirmations.get(position).getTime());
        holder.companyTxtView.setText(confirmations.get(position).getCompany());
        holder.regTxtView.setText(confirmations.get(position).getRegnum());
        holder.idtv.setText(confirmations.get(position).getId());
        holder.SeatTxt.setText(seatnum);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTxtView, SeatTxt,pickupTxtView, destinationTxtView, idtv,companyTxtView, timeTxtView, regTxtView;
        ImageView image;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            dateTxtView = (TextView) itemView.findViewById(R.id.date_txt);
            pickupTxtView = (TextView) itemView.findViewById(R.id.pickup_txt);
            destinationTxtView = (TextView) itemView.findViewById(R.id.destination_txt);
            timeTxtView = (TextView) itemView.findViewById(R.id.time_txt);
            companyTxtView = (TextView) itemView.findViewById(R.id.company_txt);
            regTxtView = (TextView) itemView.findViewById(R.id.reg_txt);
            idtv = (TextView) itemView.findViewById(R.id.bookid);
            SeatTxt = (TextView) itemView.findViewById(R.id.seat_txt);

            btn = (Button)itemView.findViewById(R.id.btn);
            image = (ImageView)itemView.findViewById(R.id.image);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = idtv.getText().toString().trim();
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        image.setImageBitmap(bitmap);
                    }
                    catch (WriterException e){
                        e.printStackTrace();
                    }

                }
            });


        }
    }
}
