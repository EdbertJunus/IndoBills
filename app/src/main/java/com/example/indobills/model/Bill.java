package com.example.indobills.model;

public class Bill {
    private String BillId;
    private String BillType;
    private String BillProviderName;
    private Integer BillProviderNumber;

    public Bill(String billId, String billType, String billProviderName, Integer billProviderNumber) {
        BillId = billId;
        BillType = billType;
        BillProviderName = billProviderName;
        BillProviderNumber = billProviderNumber;
    }

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    public String getBillProviderName() {
        return BillProviderName;
    }

    public void setBillProviderName(String billProviderName) {
        BillProviderName = billProviderName;
    }

    public Integer getBillProviderNumber() {
        return BillProviderNumber;
    }

    public void setBillProviderNumber(Integer billProviderNumber) {
        BillProviderNumber = billProviderNumber;
    }
}
