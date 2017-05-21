package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Creditor;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import static android.content.ContentValues.TAG;

/**
 * Created by rohanagarwal94 on 21/5/17.
 */

public class ReceivedRequestActivity extends AppCompatActivity {

    private HoloCircleSeekBar seekBar;
    private String names, titles, others;
    private int amount;
    private Button pay;
    private TextView name, title, other;
    Loan loan;
    private String key, number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_request_fragment);
        key = getIntent().getStringExtra("key");
        number = getIntent().getStringExtra("number");
        seekBar = (HoloCircleSeekBar) findViewById(R.id.picker);
        name = (TextView) findViewById(R.id.name1);
        title = (TextView) findViewById(R.id.title1);
        other = (TextView) findViewById(R.id.other1);
        pay = (Button) findViewById(R.id.finance);
        /*
        seekBar.getValue();
        seekBar.setMax(amount);
        */
        updateUIParameters();
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setWalletAmount(prefManager.getWalletAmount() - seekBar.getValue());
                updateParametersRemotely();
            }
        });
        /*
        name.setText(names);
        title.setText(titles);
        other.setText(others);
        */
    }

    private void updateParametersRemotely() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        // updating our wallet
        reference.child(new PrefManager(getApplicationContext()).getNameAndNumber()[1]).child("walletAmount").setValue(new PrefManager(getApplicationContext()).getWalletAmount());
        // update amount left on requester side
        reference.child(number).child("sendLoans").child(key).child("amountLeft").setValue(loan.getAmountLeft() - seekBar.getValue());
        reference.child(number).child("walletAmount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int currentAmount = Integer.parseInt(dataSnapshot.getValue().toString());
                reference.child(number).child("walletAmount").setValue(currentAmount + seekBar.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Creditor creditor = new Creditor();
        creditor.setAmountLoaned(seekBar.getValue());
        creditor.setFirebaseID(FirebaseInstanceId.getInstance().getToken());
        creditor.setMobileNumber(new PrefManager(this).getNameAndNumber()[1]);
        creditor.setName(new PrefManager(this).getNameAndNumber()[0]);
        reference.child(number).child("sendLoans").child(key).child("creditors").child(new PrefManager(this).getNameAndNumber()[1]).setValue(creditor);
        Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateUIParameters() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(number).child("sendLoans").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("asdf", dataSnapshot.toString());
                loan = dataSnapshot.getValue(Loan.class);
                Log.i(TAG, loan.getName());
                name.setText(loan.getName());
                title.setText(loan.getTitle());
                seekBar.setMax((int) loan.getAmountLeft());
                seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                        holoCircleSeekBar.setValue(i);
                    }

                    @Override
                    public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReceivedRequestActivity.this, "Error in updating UI elements", Toast.LENGTH_SHORT).show();
            }
        });
    }

}