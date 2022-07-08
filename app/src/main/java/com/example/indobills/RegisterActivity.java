package com.example.indobills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indobills.model.User;
import com.example.indobills.model.UserHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, handphone, confirm_password;
    private Button register, redirect;
    private UserHelper userHelper;
    private String toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init(){
        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_password);
        handphone = findViewById(R.id.et_register_handphone);
        confirm_password = findViewById(R.id.et_register_confirm_pass);

        register = findViewById(R.id.btn_register);
        redirect = findViewById(R.id.btn_register_redirect);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                String handphoneStr = handphone.getText().toString();
                String confirmStr = confirm_password.getText().toString();

                if (usernameStr.isEmpty() || passwordStr.isEmpty() || handphoneStr.isEmpty() || confirmStr.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All input form must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    //Validation
                    boolean phoneValidity = checkPhone(handphoneStr);
                    boolean passwordValidity = checkPassword(passwordStr);
                    userHelper = new UserHelper(RegisterActivity.this);
                    userHelper.open();
                    User user = userHelper.findUserByUsername(usernameStr);

                    //Username Validation
                    if (usernameStr.contains(" ")) {
                        Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_username_space), Toast.LENGTH_SHORT).show();
                        return;
                    }else if(user != null){
                        Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_username_exist), Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!passwordStr.equals(confirmStr)) {
                        //Validation Confirm Password
                        Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_confirm_password), Toast.LENGTH_SHORT).show();
                        return;
                    }else if(phoneValidity && passwordValidity){


                        User newUser = new User(usernameStr, passwordStr, handphoneStr);
                        userHelper.insertNewUser(newUser);
                        userHelper.close();
                        Toast.makeText(RegisterActivity.this, getResources().getText(R.string.info_register_success), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkNumericPhone(String phone){
        int length = phone.length();
        int numOfDigit = 0;
        int numOfAlpha = 0;
        for(int i=0; i<length; i++){
            char character = phone.charAt(i);
            if(Character.isLetter(character)){
                numOfAlpha++;
            }else if(Character.isDigit(character)){
                numOfDigit++;
            }
        }
        if(numOfAlpha > 0){
            return false;
        }else if(numOfDigit > 0){
            return true;
        }
        return false;
    }

    private boolean checkPhone(String phone){
        int length = phone.length();

        if(length < 10 || length > 12){
            Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_phone_numberDigits), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!phone.startsWith("08")){
            Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_phone), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!checkNumericPhone(phone)){
            Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_phone_mustBeNumber), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkAlphaNumeric(String password){
        int length = password.length();
        int numOfDigit = 0;
        int numOfAlpha = 0;
        for(int i=0; i<length; i++){
            char character = password.charAt(i);
            if(Character.isLetter(character)){
                numOfAlpha++;
            }else if(Character.isDigit(character)){
                numOfDigit++;
            }
        }
        if(numOfAlpha > 0 && numOfDigit > 0){
            return true;
        }
        return false;
    }

    private boolean checkPassword(String password){
        int length = password.length();
        if(length < 8){
            Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_passwordLength), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!checkAlphaNumeric(password)){
            Toast.makeText(RegisterActivity.this, getResources().getText(R.string.error_password_alphaNumeric), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
