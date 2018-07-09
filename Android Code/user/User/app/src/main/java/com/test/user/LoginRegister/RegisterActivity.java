package com.test.user.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.user.BaseActivity;
import com.test.user.R;
import com.test.user.Ticket.ProfileActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private DatabaseReference userInfo, list, addlist;
    private EditText editTextName, editTextPhone;
    private TextView textViewSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();



        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        progressDialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Password field is empty",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password field is empty",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Username field is empty",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Phone Number field is empty",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering,Please Wait");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(RegisterActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            saveUserInformation();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(RegisterActivity.this,"Email is already exist",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void saveUserInformation(){
        String username = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        UserInformation userInformation = new UserInformation(username,phone);

        //this is for add child of child
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userInfo = databaseReference.child("User");
        list = userInfo.child(user.getUid());
        addlist = list.child("Details");
        addlist.setValue(userInformation);

    }


    @Override
    public void onClick(View view) {
        //calling register method on click
        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
