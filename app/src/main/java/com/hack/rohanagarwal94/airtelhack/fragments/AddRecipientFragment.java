package com.hack.rohanagarwal94.airtelhack.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.util.ContactPickerMulti;

import static android.app.Activity.RESULT_OK;

/**
 * Created by viveksb007 on 12/4/17.
 */

public class AddRecipientFragment extends Fragment {

    Button btnAddRecipient;
    EditText etPhoneNumber, etAccountNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_recipient_fragment, container, false);
        btnAddRecipient = (Button) v.findViewById(R.id.btn_add_recipient);
        etPhoneNumber = (EditText) v.findViewById(R.id.amount);
        etAccountNumber = (EditText) v.findViewById(R.id.title);
        btnAddRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContacts=new Intent(getActivity(), ContactPickerMulti.class);
                startActivityForResult(pickContacts, 1);
            }
        });


//        ContentResolver cr = getActivity().getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(
//                        ContactsContract.Contacts.DISPLAY_NAME));
//
//                if (cur.getInt(cur.getColumnIndex(
//                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        String phoneNo = pCur.getString(pCur.getColumnIndex(
//                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.d("AddRecipientFragment","Name: " + name
//                                + ", Phone No: " + phoneNo);
//                    }
//                    pCur.close();
//                }
//            }
//        }
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {

                String[] temp = data.getStringArrayExtra("PICK_CONTACT");
                for(int i=0;i<temp.length;i++){
                    if(temp[i].length()==13) {
                        String str = temp[i].substring(3);
                        Log.i("contacts", " " + str);
                    }
                    else if(temp[i].length()==10){
                        Log.i("contacts", " " + temp[i]);
                    }
                }

            }
        }
    }
}
