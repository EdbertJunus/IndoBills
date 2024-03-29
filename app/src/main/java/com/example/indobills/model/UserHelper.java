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

    public User findUserByUsername(String userName){
        String query = "SELECT * FROM MsUser WHERE user_name = '" + userName + "'";
        Cursor cursor = db.rawQuery(query, null);

        User user = null;
        String UserName, UserPassword, UserHandphone, UserId;

        if(cursor.moveToFirst()){
            do{
                UserId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                UserName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                UserPassword = cursor.getString(cursor.getColumnIndexOrThrow("user_password"));
                UserHandphone = cursor.getString(cursor.getColumnIndexOrThrow("user_handphone"));

                user = new User(UserName, UserPassword, UserHandphone);
                user.setUserId(UserId);
            }while(cursor.moveToNext());
        }
        return user;
    }

    public User findUserByUserId(String userId){
        String query = "SELECT * FROM MsUser WHERE user_id = '" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        User user = null;
        String UserName, UserPassword, UserHandphone, UserId;

        if(cursor.moveToFirst()){
            do{
                UserId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                UserName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                UserPassword = cursor.getString(cursor.getColumnIndexOrThrow("user_password"));
                UserHandphone = cursor.getString(cursor.getColumnIndexOrThrow("user_handphone"));

                user = new User(UserName, UserPassword, UserHandphone);
                user.setUserId(UserId);
            }while(cursor.moveToNext());
        }
        return user;
    }

    public void insertNewUser(User newUser){
        String query = "INSERT INTO MsUser VALUES " +
                "((hex(randomblob(16))), '" + newUser.getUserName() + "', '"+ newUser.getUserPassword() + "', '"+ newUser.getUserHandphone() + "')";
        System.out.println(query);
        db.execSQL(query);
    }

}
