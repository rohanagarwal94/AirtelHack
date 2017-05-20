package com.hack.rohanagarwal94.airtelhack.model;

import java.util.ArrayList;

/**
 * Created by lenovo on 20/05/2017.
 */

public class Loan {
    String title;
    int type;
    String name;

    private float amountTotal;
    private float amountLeft;
    private ArrayList<Creditor> creditors;

    public Loan(){

    }

    public Loan(String name, String title, int type, float amountTotal, float amountLeft){
        this.name = name;
        this.title = title;
        this.type = type;
        this.amountLeft=amountLeft;
        this.amountTotal = amountTotal;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(float amountLeft) {
        this.amountLeft = amountLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
