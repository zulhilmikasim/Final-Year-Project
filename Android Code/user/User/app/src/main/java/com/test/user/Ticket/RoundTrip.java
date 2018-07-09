package com.test.user.Ticket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.test.user.schedule.FromScheduleActivity;

import java.util.Calendar;

public class RoundTrip extends Fragment implements View.OnClickListener {

    private static final String TAG = "SecondFragment";

    private DatabaseReference db;
    private DatabaseReference ticketlist, list, addlist;
    private EditText fromDisplayDate, toDisplayDate;
    private DatePickerDialog.OnDateSetListener fromDateSetListener, toDateSetListener;
    AutoCompleteTextView text1, text2;
    String[] languages = {"Penang", "Melaka"};
    Button btn;
    Spinner spin;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_round_trip, container, false);

        text1 = (AutoCompleteTextView) view.findViewById(R.id.edOrigin);
        text2 = (AutoCompleteTextView) view.findViewById(R.id.edDestination);
        fromDisplayDate = (EditText) view.findViewById(R.id.eddate1);
        toDisplayDate = (EditText) view.findViewById(R.id.eddate2);
        btn = (Button) view.findViewById(R.id.btSearch);

        spin = (Spinner) view.findViewById(R.id.spinner);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, languages);

        text1.setAdapter(adapter);
        text1.setThreshold(1);
        text2.setAdapter(adapter);
        text2.setThreshold(1);

        db = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(this);

        fromDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        fromDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                String date =  String.format("%02d", day)  + "/" + String.format("%02d", month) + "/" + year;
                fromDisplayDate.setText(date);
            }
        };

        toDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        toDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                String date = String.format("%02d", day)  + "/" + String.format("%02d", month) + "/" + year;
                toDisplayDate.setText(date);
            }
        };

        SharedPreferences pref = this.getActivity().getSharedPreferences("retpref", Context.MODE_PRIVATE);
        editor=pref.edit();


        editor.putString("numpass", spin.getSelectedItem().toString());
        editor.commit();

        return view;
    }

    private void SaveTicketInformation() {
        String Passenger = spin.getSelectedItem().toString().trim();

        ReturnTicketInformation returnTicketInformation= new ReturnTicketInformation(Passenger);

        //this is for add child of child
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ticketlist = db.child("User");
        list = ticketlist.child(user.getUid());
        addlist = list.child("Passenger");
        addlist.setValue(returnTicketInformation);
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            SaveTicketInformation();
            Intent intent = new Intent(v.getContext(), FromScheduleActivity.class);
            intent.putExtra("Origin", text1.getText().toString());
            intent.putExtra("Destination", text2.getText().toString());
            intent.putExtra("Date", fromDisplayDate.getText().toString());
            intent.putExtra("RDate", toDisplayDate.getText().toString());
            v.getContext().startActivity(intent);
        }
    }
}