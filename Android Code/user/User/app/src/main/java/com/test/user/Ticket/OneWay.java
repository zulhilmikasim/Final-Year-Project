package com.test.user.Ticket;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.user.R;
import com.test.user.schedule.ScheduleActivity;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class OneWay extends Fragment implements View.OnClickListener {
    private static final String TAG = "FirstFragment";

    private DatabaseReference db;
    private DatabaseReference ticketlist, list, addlist;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    AutoCompleteTextView text1,text2;
    String[] languages = {"Penang", "Melaka"};
    Button btn;
    Spinner spin;
    SharedPreferences.Editor editor;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_one_way, container, false);

        text1=(AutoCompleteTextView)v.findViewById(R.id.edOrigin);
        text2 = (AutoCompleteTextView)v.findViewById(R.id.edDestination);
        mDisplayDate = (EditText) v.findViewById(R.id.eddate1);
        btn = (Button) v.findViewById(R.id.btSearch);
        spin = (Spinner) v.findViewById(R.id.spinner);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,languages);

        text1.setAdapter(adapter);
        text1.setThreshold(1);
        text2.setAdapter(adapter);
        text2.setThreshold(1);

        db = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(this);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
               // Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);
                String date =  String.format("%02d", day)  + "/" + String.format("%02d", month) + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor=pref.edit();


        editor.putString("numpass", spin.getSelectedItem().toString());
        editor.commit();
        return v;
    }

    private void SaveTicketInformation(){

        String Passenger = spin.getSelectedItem().toString().trim();

        TicketInformation ticketInformation = new TicketInformation(Passenger);

        //this is for add child of child
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ticketlist = db.child("User");
        list = ticketlist.child(user.getUid());
        addlist = list.child("Passenger");
        addlist.setValue(ticketInformation);

    }

    @Override
    public void onClick(View v) {
        if(v == btn){
            SaveTicketInformation();
            Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
            intent.putExtra("Origin", text1.getText().toString());
            intent.putExtra("Destination", text2.getText().toString());
            intent.putExtra("Date", mDisplayDate.getText().toString());
            v.getContext().startActivity(intent);

        }
    }


}
