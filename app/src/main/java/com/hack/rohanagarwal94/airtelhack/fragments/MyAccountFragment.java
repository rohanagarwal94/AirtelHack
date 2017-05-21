package com.hack.rohanagarwal94.airtelhack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.activity.CreateLoanRequest;
import com.hack.rohanagarwal94.airtelhack.model.User;

import java.util.ArrayList;

/**
 * Created by viveksb007 on 12/4/17.
 */

public class MyAccountFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference myRef;
    String walletAmount="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_account_fragment, container, false);
        PrefManager manager = new PrefManager(getContext());
        TextView name = (TextView) v.findViewById(R.id.tv_name);
        TextView number = (TextView) v.findViewById(R.id.tv_phone_number);
        final TextView walletBalance = (TextView) v.findViewById(R.id.tv_wallet_balance);
        FloatingActionButton btnCreateLoanRequest = (FloatingActionButton) v.findViewById(R.id.fab_create_loan_request);
        btnCreateLoanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateLoanRequest.class);
                startActivity(intent);
            }
        });
        String[] data = manager.getNameAndNumber();
        name.setText(data[0]);
        number.setText(data[1]);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(data[1]+"/walletAmount");
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                walletAmount=dataSnapshot.getValue().toString();
                Log.i("MyAccountFragment",walletAmount);
                walletBalance.setText(walletAmount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("MyAccountFragment", "onCancelled", databaseError.toException());
                // ...
            }
        };

        myRef.addValueEventListener(postListener);
        walletBalance.setText(walletAmount);
        return v;
    }
}
