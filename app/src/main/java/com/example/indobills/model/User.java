package com.example.indobills.model;

public class User {
    private String UserId;
    private String UserName;
    private String UserPassword;
    private String UserHandphone;

    public User(String userName, String userPassword, String userHandphone) {
        UserName = userName;
        UserPassword = userPassword;
        UserHandphone = userHandphone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserHandphone() {
        return UserHandphone;
    }

    public void setUserHandphone(String userHandphone) {
        UserHandphone = userHandphone;
    }
}
