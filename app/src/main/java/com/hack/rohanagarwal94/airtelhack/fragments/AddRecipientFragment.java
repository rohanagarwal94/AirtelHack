package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;

/**
 * Created by viveksb007 on 12/4/17.
 */

public class AddRecipientFragment extends Fragment {

    Button btnAddRecipient;
    EditText etPhoneNumber, etAccountNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_recipeint_fragment, container, false);
        btnAddRecipient = (Button) v.findViewById(R.id.btn_add_recipient);
        etPhoneNumber = (EditText) v.findViewById(R.id.et_phone_number);
        etAccountNumber = (EditText) v.findViewById(R.id.et_account_number);
        btnAddRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getContext());
                prefManager.mapPhoneNumber(etPhoneNumber.getText().toString(), etAccountNumber.getText().toString());
                Toast.makeText(getContext(), "Recipient Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
