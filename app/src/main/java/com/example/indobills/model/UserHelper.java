package com.example.indobills.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class UserHelper {
    private Context ctx;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public UserHelper(Context context){
        this.ctx = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public User findUserById(String id){
        String query = "SELECT * FROM MsUser WHERE user_id = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        User user = null;
        String UserName, UserPassword, UserHandphone;

        if(cursor != null){
            do{
                UserName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                UserPassword = cursor.getString(cursor.getColumnIndexOrThrow("user_password"));
                UserHandphone = cursor.getString(cursor.getColumnIndexOrThrow("user_handphone"));

                user = new User(UserName, UserPassword, UserHandphone);

            }while(cursor.moveToNext());
        }
        return user;
    }

    public void insertNewUser(User newUser){
        String query = "INSERT INTO MsUser VALUES " +
                "(hex(randomblob(16))), '" + newUser.getUserName() + "', '"+ newUser.getUserPassword() + "', '"+ newUser.getUserHandphone() + "')";
        db.execSQL(query);
    }

}
