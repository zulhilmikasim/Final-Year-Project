package com.test.user.Receipt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.test.user.BaseActivity;
import com.test.user.LoginRegister.UserInformation;
import com.test.user.Payment.ConfirmationActivity;
import com.test.user.Payment.ConfirmationAdapter;
import com.test.user.Payment.PayPalConfig;
import com.test.user.R;
import com.test.user.Ticket.TicketInformation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TicketConfirmationActivity extends BaseActivity {

    private Button buttonPay;
    String spinInt;
    public double Amount, Total;
    private RecyclerView mProductReview;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ScheduleSummary> summary = new ArrayList<>();
    private ArrayList<TicketInformation> passangers = new ArrayList<>();
    private ScheduleSummaryAdapter mAdapter;
    private FirebaseAuth firebaseAuth;
    public int numpassenger;
    //Payment Amount
    public String paymentAmount,price;
    private ArrayList<UserInformation> userInfo =new ArrayList<>();
    public String passname,num;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_content);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_ticket_confirmation, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        setTitle("Confirm Checkout");

        buttonPay = (Button) findViewById(R.id.buttonPay);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the amount from editText
                Log.d("sec : ", price);
                Amount = Double.parseDouble(price);

                numpassenger = Integer.parseInt(spinInt);
                Total = Amount*numpassenger;
                paymentAmount = String.valueOf(Total);
                Log.d("paymentAmount :: ", String.valueOf(Amount));
                //Creating a paypalpayment
                PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "MYR", "BookMe Booking Fee",
                        PayPalPayment.PAYMENT_INTENT_SALE);
                //Creating Paypal Payment activity intent
                Intent intent = new Intent(TicketConfirmationActivity.this, PaymentActivity.class);
                //putting the paypal configuration to the intent
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra("payment", paymentAmount);
                //Puting paypal payment to the intent
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                //Starting the intent activity for result
                //the request code will be used on the method onActivityResult
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
            }
        });


        init();


        Intent i = new Intent(this, PayPalService.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(i);
    }

    private void init(){
        mProductReview = (RecyclerView)findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(this);
        mProductReview.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduleSummaryAdapter(this, summary,passangers);
        mProductReview.setAdapter(mAdapter);
        fetchdata();
        fetchSpin();
    }

    private void fetchdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Ticket");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                summary.clear();
                ScheduleSummary item = dataSnapshot.getValue(ScheduleSummary.class);
                summary.add(item);
                price = item.getPrice();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchSpin() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("User").child(user.getUid()).child("Passenger");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                passangers.clear();
                TicketInformation item = dataSnapshot.getValue(TicketInformation.class);
                passangers.add(item);
                spinInt = item.getPassenger();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));
                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}
