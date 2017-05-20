package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by viveksb007 on 20/5/17.
 */

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

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
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void sendData() {
        // send data to firebase with firebase ID
        PrefManager manager = new PrefManager(this);
        manager.setNameAndNumber(etUserName.getText().toString(), etPhoneNumber.getText().toString());
        manager.setFirstTime(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
