package com.hack.rohanagarwal94.airtelhack.model;

import java.util.ArrayList;

/**
 * Created by lenovo on 20/05/2017.
 */

public class Loan {
    private float amountTotal;
    private float amountLeft;
    private ArrayList<Creditor> creditors;

    public ArrayList<Creditor> getCreditors() {
        return creditors;
    }

    public void setCreditors(ArrayList<Creditor> creditors) {
        this.creditors = creditors;
    }

    public float getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(float amountTotal) {
        this.amountTotal = amountTotal;
    }

    public float getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(float amountLeft) {
        this.amountLeft = amountLeft;
    }
}
