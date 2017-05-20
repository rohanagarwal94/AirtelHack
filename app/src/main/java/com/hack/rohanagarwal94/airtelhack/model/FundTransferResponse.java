package com.hack.rohanagarwal94.airtelhack.model;

/**
 * Created by rohanagarwal94 on 16/4/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundTransferResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("destination_accountno")
    @Expose
    private String destinationAccountno;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("referance_no")
    @Expose
    private String referanceNo;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("payee_name")
    @Expose
    private String payeeName;
    @SerializedName("payee_id")
    @Expose
    private String payeeId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDestinationAccountno() {
        return destinationAccountno;
    }

    public void setDestinationAccountno(String destinationAccountno) {
        this.destinationAccountno = destinationAccountno;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReferanceNo() {
        return referanceNo;
    }

    public void setReferanceNo(String referanceNo) {
        this.referanceNo = referanceNo;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}