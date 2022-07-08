package com.example.indobills.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String name = "IndoBillsDB";
    private final static SQLiteDatabase.CursorFactory factory = null;
    private final static int version = 1;

    public DatabaseHelper(Context ctx){
        super(ctx, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE MsUser (user_id, user_name, user_password, user_handphone)";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE MsBill (bill_id, bill_type, bill_provider_name, bill_provider_number)";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE MsTransaction (transaction_id, transaction_date, bill_id, transaction_amount, transaction_payment_method, transaction_status, user_id)";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO MsBill VALUES " +
                "(hex(randomblob(16)), 'Wifi', 'Indihome', 123456), " +
                "(hex(randomblob(16)), 'Phone', 'Telkom', 021123456789), " +
                "(hex(randomblob(16)), 'Water', 'PDAM', 100340)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS MsUser";
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS MsBill";
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS MsTransaction";
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }
}
