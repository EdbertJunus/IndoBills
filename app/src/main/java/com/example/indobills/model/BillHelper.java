package com.example.indobills.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
        Integer BillProviderNumber;

        if(cursor != null){
            do{
                BillType = cursor.getString(cursor.getColumnIndexOrThrow("bill_type"));
                BillProviderName = cursor.getString(cursor.getColumnIndexOrThrow("bill_provider_name"));
                BillProviderNumber = cursor.getInt(cursor.getColumnIndexOrThrow("bill_provider_number"));

                bill = new Bill(BillId, BillType, BillProviderName, BillProviderNumber);

            }while(cursor.moveToNext());
        }
        return bill;
    }
}
