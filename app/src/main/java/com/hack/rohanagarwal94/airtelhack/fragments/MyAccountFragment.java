package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.User;

/**
 * Created by viveksb007 on 12/4/17.
 */

public class MyAccountFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            /*
    Call this API to fetch details
    https://retailbanking.mybluemix.net/banking/icicibank/account_summary?client_id=<participant_id>&token=<access token>&custid=<customer identification number>&accountno=<account number>
     */
            /*
            Inject response details in views

                [{"code":200},
            {"vpa":"xyz@icicibank",
            "sub_product_type":"Regular Savings Account",
            "accounttype":"Savings Account",
            "wallet_balance":"275",
            "account_status":"Active",
            "mobileno":"7977711111",
            "product_desc":"Regular Savings Account",
            "wallet_id":"5001",
            "product_type":"Savings Account",
            "balance":"462510.8188610320",
            "accountno":"4444777755550001",
            "reward_point":"1482",
            "custid":"33335001",
            "product_category":"Liabilities"}
            ]
            */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        User user = new User();
        user.setName("Rishabh");
        user.setFirebaseID(FirebaseInstanceId.getInstance().getToken());
        user.setWalletAmount(100);
        user.setLoans(null);
        myRef.child("8375089216").setValue(user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                Log.d("MyAccountFragment", "Value is: " );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MyAccountFragment", "Failed to read value.", error.toException());
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_account_fragment, container, false);
        return v;
    }
}
