package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hack.rohanagarwal94.airtelhack.R;

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
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_account_fragment, container, false);
        return v;
    }
}
