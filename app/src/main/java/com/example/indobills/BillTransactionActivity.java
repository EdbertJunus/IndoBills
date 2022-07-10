package com.example.indobills;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indobills.model.Bill;
import com.example.indobills.model.BillHelper;
import com.example.indobills.model.Transaction;
import com.example.indobills.model.TransactionHelper;
import com.example.indobills.model.UserHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillTransactionActivity extends AppCompatActivity {

    private String textBills, textProvider, providerNameInput, providerNumberInput, providerViaInput;
    private TextView tvProvider, tvNumber, tvOvo, tvGopay;
    private Boolean ovoClick, gopayClick;
    private EditText etAmountInput;
    private Button btnSubmit;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        providerNameInput = getIntent().getStringExtra("providerName");
        providerNumberInput = getIntent().getStringExtra("providerNumber");
        textBills = getIntent().getStringExtra("billType");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_transaction);

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

            tvProvider = findViewById(R.id.tv_provider_name);
            tvNumber = findViewById(R.id.tv_provider_number);

            tvProvider.setText(textProvider + " Provider: "+providerNameInput);
            tvNumber.setText(textProvider + " Number: "+providerNumberInput);

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
        btnSubmit = findViewById(R.id.btn_submit_transaction);
        etAmountInput = findViewById(R.id.et_bill_transaction_amount);
        tvOvo = findViewById(R.id.tv_payment_ovo);
        tvGopay = findViewById(R.id.tv_oayment_gopay);
        ovoClick = false;
        gopayClick = false;

        tvOvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ovoClick == false){
                    ovoClick = true;
                    tvOvo.setTypeface(Typeface.DEFAULT_BOLD);
                }else{
                    ovoClick = false;
                    tvOvo.setTypeface(Typeface.DEFAULT);
                }
                gopayClick = false;
                tvGopay.setTypeface(Typeface.DEFAULT);


            }
        });

        tvGopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gopayClick == false){
                    gopayClick = true;
                    tvGopay.setTypeface(Typeface.DEFAULT_BOLD);
                }else{
                    gopayClick = false;
                    tvGopay.setTypeface(Typeface.DEFAULT);
                }
                ovoClick = false;
                tvOvo.setTypeface(Typeface.DEFAULT);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountStr = etAmountInput.getText().toString();
                if(amountStr.isEmpty()){
                    Toast.makeText(BillTransactionActivity.this, getResources().getString(R.string.error_amount), Toast.LENGTH_SHORT).show();
                    return;
                }else if((!gopayClick && !ovoClick) || (gopayClick && ovoClick)){
                    Toast.makeText(BillTransactionActivity.this, getResources().getString(R.string.error_payment_type), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BillTransactionActivity.this, TransactionActivity.class);
                intent.putExtra("billType", textBills);
                intent.putExtra("inputBillName", providerNameInput);
                intent.putExtra("inputBillNumber", providerNumberInput);
                intent.putExtra("inputBillType",textProvider);
                intent.putExtra("inputBillAmount", amountStr);

                if(gopayClick) providerViaInput = "Gopay";
                else if(ovoClick) providerViaInput = "OVO";

                intent.putExtra("inputBillVia", providerViaInput);
                startActivity(intent);
            }
        });
    }
}
