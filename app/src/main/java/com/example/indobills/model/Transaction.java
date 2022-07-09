package com.example.indobills.model;

import java.util.Date;

public class Transaction {
    private String TransactionId;
    private Date TransactionDate;
    private String BillId;
    private String TransactionAmount;
    private String TransactionPaymentMethod;
    private Boolean TransactionStatus;
    private String UserId;

    public Transaction(Date transactionDate, String billId, String transactionAmount, String transactionPaymentMethod, Boolean transactionStatus, String userId) {
        TransactionDate = transactionDate;
        BillId = billId;
        TransactionAmount = transactionAmount;
        TransactionPaymentMethod = transactionPaymentMethod;
        TransactionStatus = transactionStatus;
        UserId = userId;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    public String getTransactionPaymentMethod() {
        return TransactionPaymentMethod;
    }

    public void setTransactionPaymentMethod(String transactionPaymentMethod) {
        TransactionPaymentMethod = transactionPaymentMethod;
    }

    public Boolean getTransactionStatus() {
        return TransactionStatus;
    }

    public void setTransactionStatus(Boolean transactionStatus) {
        TransactionStatus = transactionStatus;
    }
}
