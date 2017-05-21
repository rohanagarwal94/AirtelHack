package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.User;

import butterknife.OnClick;

/**
 * Created by viveksb007 on 20/5/17.
 */

public class SignUpActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etPhoneNumber;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager manager = new PrefManager(this);
        if (!manager.isFirstTime()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.sign_up);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    public void sendData() {
        User user = new User();
        user.setName(etUserName.getText().toString());
        user.setFirebaseID(FirebaseInstanceId.getInstance().getToken());
        user.setWalletAmount(1000);
        user.setLoans(null);
        myRef.child(etPhoneNumber.getText().toString()).setValue(user);
        PrefManager manager = new PrefManager(this);
        manager.setNameAndNumber(etUserName.getText().toString(), etPhoneNumber.getText().toString());
        manager.setWalletAmount(1000);
        manager.setFirstTime(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
