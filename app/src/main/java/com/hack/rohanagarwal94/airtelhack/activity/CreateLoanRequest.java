package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.android.ex.chips.recipientchip.DrawableRecipientChip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.hack.rohanagarwal94.airtelhack.util.ContactPickerMulti;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by viveksb007 on 20/5/17.
 */

public class CreateLoanRequest extends AppCompatActivity {

    Button btnAddRecipient;
    EditText etTitle, etAmount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_loan_request);
        //btnAddRecipient = (Button) findViewById(R.id.btn_add_recipient);
        etTitle = (EditText) findViewById(R.id.title);
        etAmount = (EditText) findViewById(R.id.amount);
        /*
        btnAddRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContacts = new Intent(CreateLoanRequest.this, ContactPickerMulti.class);
                startActivityForResult(pickContacts, 1);
            }
        });
        */

        final RecipientEditTextView phoneRetv =
                (RecipientEditTextView) findViewById(R.id.phone_retv);
        phoneRetv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        BaseRecipientAdapter adapter = new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this);
        adapter.setShowMobileOnly(true);
        phoneRetv.setAdapter(adapter);
        phoneRetv.dismissDropDownOnItemSelected(true);

        final ImageButton showAll = (ImageButton) findViewById(R.id.show_all);
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneRetv.showAllContacts();
            }
        });
        Button btnSubmitLoadRequest = (Button) findViewById(R.id.btn_submit_load_request);
        btnSubmitLoadRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> phoneNumbers = new ArrayList<>();
                DrawableRecipientChip[] chips = phoneRetv.getSortedRecipients();
                for (DrawableRecipientChip chip : chips) {
                    //Log.v("DrawableChip", chip.getEntry().getDisplayName() + " " + chip.getEntry().getDestination());
                    phoneNumbers.add(chip.getEntry().getDestination().replace(" ", "").substring(3));
                }
                Log.v("Contact", phoneNumbers.toString());
                pushLoanRequest();
            }
        });
    }

    public void pushLoanRequest() {
        PrefManager manager = new PrefManager(this);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(manager.getNameAndNumber()[1]).child("sendLoans");
        Loan loan = new Loan(manager.getNameAndNumber()[0], etTitle.getText().toString(), new Random().nextInt(2), Float.parseFloat(etAmount.getText().toString()));
        reference.setValue(loan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String[] temp = data.getStringArrayExtra("PICK_CONTACT");
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].length() == 13) {
                        String str = temp[i].substring(3);
                        Log.i("contacts", " " + str);
                    } else if (temp[i].length() == 10) {
                        Log.i("contacts", " " + temp[i]);
                    }
                }
            }
        }
    }
}
