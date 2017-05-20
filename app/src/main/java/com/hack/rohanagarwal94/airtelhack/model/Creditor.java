package com.hack.rohanagarwal94.airtelhack.model;

/**
 * Created by lenovo on 20/05/2017.
 */

public class Creditor {
    private String mobileNumber;
    private String firebaseID;
    private String name;
    private float amountLoaned;

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public float getAmountLoaned() {
        return amountLoaned;
    }

    public void setAmountLoaned(float amountLoaned) {
        this.amountLoaned = amountLoaned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
