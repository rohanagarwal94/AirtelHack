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

    public void setWalletAmount(int walletAmount) {
        editor = preferences.edit();
        editor.putInt("wallet_amount", walletAmount);
        editor.apply();
    }

    public int getWalletAmount() {
        return preferences.getInt("wallet_amount", 0);
    }

    public void setNameAndNumber(String name, String number) {
        editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("number", number);
        editor.apply();
    }

    public String[] getNameAndNumber() {
        return new String[]{preferences.getString("name", null), preferences.getString("number", null)};
    }

    public void setFirstTime(boolean isFirstTime) {
        editor = preferences.edit();
        editor.putBoolean("first_time", isFirstTime);
        editor.apply();
    }

    public boolean isFirstTime() {
        return preferences.getBoolean("first_time", true);
    }

    public void setReqQ() {
        editor = preferences.edit();
        editor.putInt("reqQ", getReqQ() + 1);
        editor.apply();
    }

    public int getReqQ() {
        return preferences.getInt("reqQ", 0);
    }

    public void addKeyNumber(String key, String number) {
        editor = preferences.edit();
        editor.putString("key" + String.valueOf(getReqQ()), key);
        editor.putString("number" + String.valueOf(getReqQ()), number);
        editor.apply();
    }

}
