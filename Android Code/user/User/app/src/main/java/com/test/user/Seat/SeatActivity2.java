package com.test.user.Seat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.user.Booking.Admin;
import com.test.user.Booking.Booking;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.R;
import com.test.user.Ticket.TicketInformation;
import com.test.user.schedule.ReturnScheduleActivity;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener{

    GridView gridView;
    ArrayList<Item2> gridArray = new ArrayList<Item2>();
    CustomGridViewAdapter2 customGridAdapter;
    DatabaseReference db;
    public Bitmap seatIcon;
    public Bitmap seatSelect;
    public Bitmap seatBook;
    Button btn;
    List<String> list = new ArrayList<String>();
    Dialog dialog;
    Button yes,no;
    TextView t1;
    private DatabaseReference seatlist, seatnum, addlist, booklist, list2;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Seat2> seats = new ArrayList<>();
    public int num;
    private String  date, time, pickup, destination, bookid, regnum, company, numpass;
    SharedPreferences pref;
    public String passname,phone;
    private ArrayList<UserInformation> userInfo = new ArrayList<>();
    private ArrayList<TicketInformation> passnum = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat2);
        //get Data from schedule activity for Booking
        Intent intent = getIntent();

        date = intent.getStringExtra("date");
        pickup = intent.getStringExtra("pickup");
        destination =  intent.getStringExtra("dest");
        time = intent.getStringExtra("time");
        company = intent.getStringExtra("company");
        regnum = intent.getStringExtra("regnum");
        bookid = intent.getStringExtra("id");

        pref=getApplication().getSharedPreferences("retpref", MODE_PRIVATE);
        numpass =pref.getString("numpass", "");
        // set grid view item
        seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_avl);
        seatSelect = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_std);
        seatBook = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_bkd);
        totalSeat(30);
        num = 1;
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter2(this, R.layout.seatrow_grid, gridArray,num);
        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(this);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeatActivity2.this, ReturnScheduleActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        seatBook(num);

    }


    public void totalSeat(int n)
    {
        for (int i = 1; i <= n; ++i)
        {
            gridArray.add(new Item2(seatIcon, "seat " + i));
        }

    }

    public void seatSelected(int pos)
    {
        gridArray.remove(pos);
        gridArray.add(pos, new Item2(seatSelect, "selected"));
        list.add(String.valueOf("Seat " + (pos+1)));
        customGridAdapter.notifyDataSetChanged();
    }

    public void seatBook(int pos)
    {
        gridArray.add(pos, new Item2(seatBook, "booked"));
        //list.add(String.valueOf("Seat " + (pos+1)));

    }

    public void seatDeselcted(int pos)
    {

        gridArray.remove(pos);
        int i = pos + 1;
        gridArray.add(pos, new Item2(seatIcon, "seat" +i ));
        list.remove(String.valueOf("Seat " + i));
        customGridAdapter.notifyDataSetChanged();
    }

    String joined;
    int count = 0;
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, int position, long id)
    {
        Item2 item = gridArray.get(position);
        Bitmap seatcompare = item.getImage();
        if (seatcompare == seatIcon)
        {
            seatSelected(position);
            count++;
            joined = TextUtils.join(", ", list);
            t1 = (TextView)findViewById(R.id.text);
            t1.setText(joined);
        }
        else {
            seatDeselcted(position);
            count--;
            list.remove(list.size()-1);
            joined = TextUtils.join(", ", list);
            t1 = (TextView)findViewById(R.id.text);
            t1.setText(joined);
        }


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user2 = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user2.getUid()).child("Passenger");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                passnum.clear();
                TicketInformation item = dataSnapshot.getValue(TicketInformation.class);
                passnum.add(item);
                String pass = item.getPassenger();
                if(count>Integer.parseInt(pass))
                {
                    gridView.setEnabled(false);
                    MyCustomAlertDialog();
                    int pos = parent.getPositionForView(view);
                    seatDeselcted(pos);
                    count--;
                    list.remove(list.size()-1);
                    joined = TextUtils.join(", ", list);
                    t1 = (TextView)findViewById(R.id.text);
                    t1.setText(joined);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user3 = firebaseAuth.getCurrentUser();
        DatabaseReference myRef2 = database2.getReference("User").child(user3.getUid()).child("Details");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo.clear();
                UserInformation item = dataSnapshot.getValue(UserInformation.class);
                userInfo.add(item);
                passname = item.getUsername();
                phone = item.getPhone();
                Seat seat = new Seat(joined);

                //this is for add child of child
                FirebaseUser user = firebaseAuth.getCurrentUser();
                seatlist = db.child("User");
                seatnum = seatlist.child(user.getUid());
                addlist = seatnum.child("Seat");
                addlist.setValue(seat);

                Booking model2 = new Booking();
                model2.setDate(date);
                model2.setPickup(pickup);
                model2.setDestination(destination);
                model2.setTime(time);
                model2.setCompany(company);
                model2.setRegnum(regnum);
                model2.setId(bookid);
                model2.setSeat(joined);
                model2.setName(passname);
                model2.setPhone(phone);

                booklist = db.child("User");
                list2 = booklist.child(user.getUid());
                list2.child("Booking").child(bookid).setValue(model2);

                Admin data = new Admin();
                data.setDate(date);
                data.setPickup(pickup);
                data.setDestination(destination);
                data.setTime(time);
                data.setCompany(company);
                data.setRegnum(regnum);
                data.setId(bookid);
                data.setSeat(joined);
                data.setNumpass(numpass);
                data.setName(passname);
                data.setPhone(phone);
                db.child("PassengerBooking").child(regnum).child(bookid).setValue(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void MyCustomAlertDialog() {
        dialog = new Dialog(SeatActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);


        yes = (Button) dialog.findViewById(R.id.btnYes);
        no = (Button) dialog.findViewById(R.id.btnNo);
        yes.setEnabled(true);
        no.setEnabled(true);



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeatActivity2.this, ReturnScheduleActivity.class));

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                gridView.setEnabled(true);
            }

        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

}

