package com.example.indobills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indobills.model.UserHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, handphone, confirm_password;
    private Button register, redirect;
    private UserHelper userHelper;
    private String toastMessage;
    private Boolean dataMatch = true;

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

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
