package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.R;

public class RequestLoanActivity extends AppCompatActivity {

    private static final String TAG = RequestLoanActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_loan);

    }
}
