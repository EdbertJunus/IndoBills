package com.example.indobills.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BillHelper {
    private Context ctx;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public BillHelper(Context context){
        this.ctx = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Bill findBillById(String BillId){
        String query = "SELECT * FROM MsBill WHERE bill_id = '" + BillId + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Bill bill = null;
        String BillType, BillProviderName;
        String BillProviderNumber;

        if(cursor != null){
            do{
                BillType = cursor.getString(cursor.getColumnIndexOrThrow("bill_type"));
                BillProviderName = cursor.getString(cursor.getColumnIndexOrThrow("bill_provider_name"));
                BillProviderNumber = cursor.getString(cursor.getColumnIndexOrThrow("bill_provider_number"));

                bill = new Bill(BillId, BillType, BillProviderName, BillProviderNumber);

            }while(cursor.moveToNext());
        }
        return bill;
    }

    public ArrayList<Bill> findBillExist(String BillName, String BillType){
        String query = "SELECT * FROM MsBill WHERE bill_provider_name = '"+ BillName +
                "' AND bill_type = '" + BillType + "'";
        Cursor cursor = db.rawQuery(query, null);

        Bill bill = null;
        String BillId, BillProviderType, BillProviderName, BillProviderNumber;
        ArrayList<Bill> billArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                BillId = cursor.getString(cursor.getColumnIndexOrThrow("bill_id"));
                BillProviderType = cursor.getString(cursor.getColumnIndexOrThrow("bill_type"));
                BillProviderName = cursor.getString(cursor.getColumnIndexOrThrow("bill_provider_name"));
                BillProviderNumber = cursor.getString(cursor.getColumnIndexOrThrow("bill_provider_number"));

                bill = new Bill(BillId, BillProviderType, BillProviderName, BillProviderNumber);
                billArrayList.add(bill);
            }while(cursor.moveToNext());
        }
        return billArrayList;
    }
}
