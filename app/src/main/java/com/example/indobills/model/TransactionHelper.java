package com.example.indobills.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionHelper {
    private Context ctx;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public TransactionHelper(Context context){
        this.ctx = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Transaction findTransactionById(String id){
        String query = "SELECT * FROM MsTransaction WHERE transaction_id = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Transaction transaction = null;
        String BillId, TransactionDate, TransactionPaymentMethod, UserId;
        Integer TransactionAmount;
        Boolean TransactionStatus;

        if(cursor != null){
            do{
                TransactionDate = cursor.getString(cursor.getColumnIndexOrThrow("transaction_date"));
                BillId = cursor.getString(cursor.getColumnIndexOrThrow("bill_id"));
                TransactionAmount = cursor.getInt(cursor.getColumnIndexOrThrow("transaction_amount"));
                TransactionPaymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("transaction_payment_method"));
                TransactionStatus = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("transaction_status")));
                UserId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = formatter.parse(TransactionDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                transaction = new Transaction(date, BillId, TransactionAmount, TransactionPaymentMethod, TransactionStatus, UserId);

            }while(cursor.moveToNext());
        }
        return transaction;
    }

    public void insertTransaction(Transaction transaction){
        String query = "INSERT INTO MsTransaction VALUES " +
                "((hex(randomblob(16))), '" + transaction.getTransactionDate() + "', '"+ transaction.getBillId() + "', '" + transaction.getTransactionAmount() + "', '"+ transaction.getTransactionPaymentMethod() + "', '" +"', '"+ transaction.getTransactionStatus() +"')";
        db.execSQL(query);
    }

    public ArrayList<Transaction> viewTransactionByUserId(String userId){
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        String query = "SELECT * FROM MsTransaction WHERE user_id = '" +userId+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Transaction transaction = null;
        String BillId, TransactionDate, TransactionPaymentMethod, UserId;
        Integer TransactionAmount;
        Boolean TransactionStatus;

        if(cursor.getCount() > 0){
            do{
                TransactionDate = cursor.getString(cursor.getColumnIndexOrThrow("transaction_date"));
                BillId = cursor.getString(cursor.getColumnIndexOrThrow("bill_id"));
                TransactionAmount = cursor.getInt(cursor.getColumnIndexOrThrow("transaction_amount"));
                TransactionPaymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("transaction_payment_method"));
                TransactionStatus = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("transaction_status")));
                UserId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = formatter.parse(TransactionDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                transaction = new Transaction(date, BillId, TransactionAmount, TransactionPaymentMethod, TransactionStatus, UserId);

                transactionArrayList.add(transaction);
                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return transactionArrayList;
    }


}
