package com.test.bookmedriver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class QRActivity extends AppCompatActivity {
    private ArrayList<Admin> mProductList = new ArrayList<>();
    private  ArrayList<String> ticketId = new ArrayList<>();
    Dialog dialog;
    Button yes,no;
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Validate").child("PKF7573");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProductList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Admin item = ds.getValue(Admin.class);
                    mProductList.add(item);
                    String id = item.getId();
                    ticketId.add(id);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // get data

        for (int i = 0 ; i < ticketId.size() ; i++)
        {
            String mt = ticketId.get(i);
            if(result != null) {

                if (result.getContents() == null) {
                    Toast.makeText(QRActivity.this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                } else if (result.getContents().equals(mt)) {
                    //Toast.makeText(QRActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Ticket is Validated", Toast.LENGTH_SHORT).show();
                    MyCustomAlertDialog();
                    break;
                }
                else if (!result.getContents().equals(mt)) {
                    //Toast.makeText(QRActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Ticket is Not Validated", Toast.LENGTH_SHORT).show();
                    WrongAlertDialog(result.getContents());

                }

            }//end of if
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }

    } //end of onActivityResult

    public void MyCustomAlertDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("My Custom Dialog");


        yes = (Button) dialog.findViewById(R.id.btnYes);
        no = (Button) dialog.findViewById(R.id.btnNo);
        gif = (GifImageView)dialog.findViewById(R.id.gif);
        yes.setEnabled(true);
        no.setEnabled(true);

        //this is for make the gif stop after 2 second
        new CountDownTimer(2000, 1000) { // 2000 = 2 sec
            GifDrawable drawable = (GifDrawable) gif.getDrawable();
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                drawable.stop();
            }
        }.start();





        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }

        });
        dialog.show();
    }

    public void WrongAlertDialog(final String result) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wrong);
        dialog.setTitle("My Custom Dialog");


        yes = (Button) dialog.findViewById(R.id.btnYes);
        no = (Button) dialog.findViewById(R.id.btnNo);
        gif = (GifImageView)dialog.findViewById(R.id.gif);
        yes.setEnabled(true);
        no.setEnabled(true);

        final Activity activity = this;
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }

        });
        dialog.show();
    }

}
