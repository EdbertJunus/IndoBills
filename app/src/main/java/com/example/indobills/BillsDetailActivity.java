package com.example.indobills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BillsDetailActivity extends AppCompatActivity {

    private String textBills, textProvider;
    private TextView tvProvider, tvNumber;
    private Button btnSearch;
    private EditText etName, etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_detail);
        textBills = getIntent().getStringExtra("billType");

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("navMenu", "Home");
            bundle.putBoolean("navBack", true);

            Bundle itemBundle = new Bundle();
            itemBundle.putString("item_text", textBills);

            if(textBills.contains("Wifi")){
                textProvider = "Wifi";
            }else if(textBills.contains("Phone")){
                textProvider = "Phone";
            }else if(textBills.contains("Water")){
                textProvider = "Water";
            }

            tvProvider = findViewById(R.id.tv_provider);
            tvNumber = findViewById(R.id.tv_number);

            tvProvider.setText(textProvider + " Provider");
            tvNumber.setText(textProvider + " Number");

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_nav_bottom, NavigationBottom.class, bundle)
                    .add(R.id.fragment_nav_top, TopNav.class, bundle)
                    .add(R.id.fragment_bill_item, BillItem.class, itemBundle)

                    .commit();
        }

        init();
    }

    private void init(){
        etName = findViewById(R.id.et_provider_name);
        etNumber = findViewById(R.id.et_provider_number);

        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String providerNameStr = etName.getText().toString();
                String providerNumberStr = etNumber.getText().toString();

                if(providerNameStr.isEmpty() || providerNumberStr.isEmpty()){
                    Toast.makeText(BillsDetailActivity.this, "Provider Name and Number must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BillsDetailActivity.this, BillTransactionActivity.class);
                intent.putExtra("billType", textBills);
                intent.putExtra("providerName", providerNameStr);
                intent.putExtra("providerNumber", providerNumberStr);

                startActivity(intent);
            }
        });
    }

}
