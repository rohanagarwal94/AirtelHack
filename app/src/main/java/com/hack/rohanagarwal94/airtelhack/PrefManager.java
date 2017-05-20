package com.hack.rohanagarwal94.airtelhack;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by viveksb007 on 16/4/17.
 */

public class PrefManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private static final String pref_name = "airtel";

    public PrefManager(Context context) {
        this.mContext = context;
        preferences = mContext.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
    }

    public void mapPhoneNumber(String phoneNumber, String accountNumber) {
        editor = preferences.edit();
        editor.putString(phoneNumber, accountNumber);
        editor.apply();
    }

    public String getAccountNumber(String phoneNumber) {
        return preferences.getString(phoneNumber, null);
    }

}
