package com.example.indobills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private String toastMessage;
    private Boolean dataMatch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                      //check if username not exist
                      if(user == null){
                          toastMessage = "Username does not exist";
                          dataMatch = false;
                      }else{
                          //check is password false
                          if(!passwordStr.equals(user.getUserPassword())){
                              toastMessage = "Password input is wrong";
                              dataMatch = false;
                          }
                      }

                      userHelper.close();
                      Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

                      //Data input is wrong
                      if(!dataMatch){
                          return;
                      }

                      //Data input is correct
                      Intent intent = new Intent(MainActivity.this, HomeActivity.class);
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