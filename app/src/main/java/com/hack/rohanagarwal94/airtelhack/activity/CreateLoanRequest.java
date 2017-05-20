package com.hack.rohanagarwal94.airtelhack.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.hack.rohanagarwal94.airtelhack.model.User;
import com.hack.rohanagarwal94.airtelhack.util.ContactPickerMulti;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by viveksb007 on 20/5/17.
 */

public class CreateLoanRequest extends AppCompatActivity {

    private static final String TAG = CreateLoanRequest.class.getSimpleName();
    Button btnAddRecipient;
    EditText etTitle, etAmount;
    String key;
    FirebaseDatabase database;
    RequestQueue requestQueue;
    DatabaseReference myRef;
    ArrayList<String> phoneNumbers;
    ArrayList<String> firebaseIds;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_loan_request);
        getPermissions();
        //btnAddRecipient = (Button) findViewById(R.id.btn_add_recipient);
        etTitle = (EditText) findViewById(R.id.title);
        etAmount = (EditText) findViewById(R.id.amount);
        phoneNumbers = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(CreateLoanRequest.this);
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
                DrawableRecipientChip[] chips = phoneRetv.getSortedRecipients();
                for (DrawableRecipientChip chip : chips) {
                    //Log.v("DrawableChip", chip.getEntry().getDisplayName() + " " + chip.getEntry().getDestination());
                    phoneNumbers.add(chip.getEntry().getDestination().replace(" ", "").substring(3));
                }
                getUsers();
                Log.v("Contact", phoneNumbers.toString());
                pushLoanRequest();
            }
        });

    }

    private void getPermissions() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
            EasyPermissions.requestPermissions(this, "This app needs to access contacts to send Loan Requests.", 0, Manifest.permission.READ_CONTACTS);
        }
    }

    public void getUsers() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        firebaseIds = new ArrayList<>();
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String mobile = data.getKey();
                    if (phoneNumbers.contains(mobile)) {
                        User user = data.getValue(User.class);
                        firebaseIds.add(user.getFirebaseID());
                        Log.i(TAG, "" + user.getFirebaseID());
                    }
                }
                sendNotifications();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "onCancelled", databaseError.toException());
                // ...
            }
        };

        myRef.addListenerForSingleValueEvent(postListener);
    }

    public void sendNotifications() {
        Log.i(TAG,firebaseIds.size()+"+");
        if (firebaseIds != null) {
            Log.i(TAG, "here1");
            for (String id : firebaseIds) {
                send(id);
            }
        }
    }

    private void send(String id) {

        final String URL = "https://fcm.googleapis.com/fcm/send";
        JSONObject object = new JSONObject();
        Log.i(TAG, "here");
        JSONObject json = new JSONObject();
        try {
            json.put("title", key);
            PrefManager manager = new PrefManager(this);
            String[] nameAndNumber = manager.getNameAndNumber();
            json.put("body", nameAndNumber[1]);
            object.put("notification", json);
            object.put("to", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request_json = new JsonObjectRequest(URL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "key=AAAAaReRW5w:APA91bE1l3m66eq2-QLt_vbNackzBfEsuaasXfNbqvmhClng40GiwL7KtV6W1wQ7DptlzYIb6zhWCtFAc4CwVWq5DJg2GXoYTLteXpe4F95bLO3HPRJNUsvfQQuZpil5QcENbbwGU--_");
                return params;
            }
        };
        requestQueue.add(request_json);

    }

    public void pushLoanRequest() {
        PrefManager manager = new PrefManager(this);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(manager.getNameAndNumber()[1]).child("sendLoans");
        Loan loan = new Loan(manager.getNameAndNumber()[0], etTitle.getText().toString(), new Random().nextInt(2), Float.parseFloat(etAmount.getText().toString()),Float.parseFloat(etAmount.getText().toString()));
        key = reference.push().getKey();
        reference.child(key).setValue(loan);
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
