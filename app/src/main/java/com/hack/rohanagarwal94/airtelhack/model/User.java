package com.hack.rohanagarwal94.airtelhack.model;

import java.util.ArrayList;

/**
 * Created by lenovo on 20/05/2017.
 */

public class User {
    private String firebaseID;
    private String name;
    private float walletAmount;
    private ArrayList<Loan> loans;

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public float getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(float walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
}
