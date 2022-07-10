package com.example.indobills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indobills.model.User;
import com.example.indobills.model.UserHelper;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login, redirect;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String UserId = getSharedPreferences("loginStatus", Context.MODE_PRIVATE).getString("UserId","");

        if(UserId.length() > 0){
            userHelper = new UserHelper(MainActivity.this);
            userHelper.open();
            User user = userHelper.findUserByUserId(UserId);
            userHelper.close();

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("userName", user.getUserName());
            intent.putExtra("userId", user.getUserId());
            startActivity(intent);
        }

        init();
    }

    private void init(){
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.btn_login);
        redirect = findViewById(R.id.btn_redirect);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                if(usernameStr.isEmpty() || passwordStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Username and Password must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                      userHelper = new UserHelper(MainActivity.this);
                      userHelper.open();
                      User user = userHelper.findUserByUsername(usernameStr);
                      userHelper.close();
                      //check if username not exist
                      if(user == null){
                          Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                          return;
                      }else if(!passwordStr.equals(user.getUserPassword())){
                          //check is password false
                          System.out.println("password: "+passwordStr+ " correct: "+user.getUserPassword());
                          Toast.makeText(MainActivity.this, "Password input is wrong", Toast.LENGTH_SHORT).show();
                          return;
                      }
                          //Data input is correct
                          SharedPreferences sp = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
                          SharedPreferences.Editor editor = sp.edit();
                          editor.putString("UserId", user.getUserId());
                          editor.commit();
                          Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                          Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          intent.putExtra("userName", user.getUserName());
                          intent.putExtra("userId", user.getUserId());
                          startActivity(intent);

                }
            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}