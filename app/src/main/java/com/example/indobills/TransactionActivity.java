package com.example.indobills;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.indobills.model.Bill;
import com.example.indobills.model.BillHelper;
import com.example.indobills.model.TransactionHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionActivity extends AppCompatActivity {

    private String textBills, billName, billType, billVia, billNumber, billAmount, transactionError;
    private TransactionHelper transactionHelper;
    private BillHelper billHelper;

    private Boolean billNumIsCorrect = false;
    private Boolean transactionStatus = true;

    private TextView tvTitle, tvMsg, tvBillName, tvBillNumber, tvBillAmount, tvBillVia;
    private LinearLayout layoutSuccess;
    private FragmentContainerView fragmentLoading, fragmentStatus;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        textBills = getIntent().getStringExtra("billType");

        billName = getIntent().getStringExtra("inputBillName");
        billNumber = getIntent().getStringExtra("inputBillNumber");
        billType = getIntent().getStringExtra("inputBillType");
        billAmount = getIntent().getStringExtra("inputBillAmount");
        billVia = getIntent().getStringExtra("inputBillVia");

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("navMenu", "Home");
            bundle.putBoolean("navBack", false);

            Bundle itemBundle = new Bundle();
            itemBundle.putString("item_text", textBills);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_nav_bottom, NavigationBottom.class, bundle)
                    .add(R.id.fragment_nav_top, TopNav.class, bundle)
                    .add(R.id.fragment_bill_item, BillItem.class, itemBundle)
                    .add(R.id.fragment_transaction_loading, LoadingTransaction.class, null)
                    .commit();
        }

        init();

    }

    private void init(){
        //check if bill exist
        billHelper = new BillHelper(TransactionActivity.this);
        billHelper.open();
        ArrayList<Bill> billArrayList = billHelper.findBillExist(billName, billType);
        billHelper.close();

        tvTitle = findViewById(R.id.tv_transaction_title);
        tvMsg = findViewById(R.id.tv_transaction_msg);
        layoutSuccess = findViewById(R.id.ll_transaction_success);

        fragmentLoading = findViewById(R.id.fragment_transaction_loading);
        fragmentStatus = findViewById(R.id.fragment_transaction_status);

        System.out.println("Phone from input: "+billNumber);
        System.out.println("DB SIZe: "+billArrayList.size() );
        for (Bill bill:billArrayList) {
            System.out.println("Phone from DB: "+bill.getBillProviderNumber().toString());
            if(bill.getBillProviderNumber().toString().equals(billNumber)){
                billNumIsCorrect = true;
            }
        }

        if(billArrayList.size() == 0){
            //Provider Name Wrong
            transactionStatus = false;
            transactionError = "Name";
        }else if(!billNumIsCorrect){
            //Provider Number Wrong
            transactionStatus = false;
            transactionError = "Number";
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Bundle navBundle = new Bundle();
        navBundle.putBoolean("navBack", true);
        navBundle.putBoolean("backToHome", true);

        if(transactionStatus){
            bundle.putBoolean("transactionStatus", true);
            bundle.putString("transactionId", "12345");
            bundle.putString("transactionProviderName", billName);
            bundle.putString("transactionProviderNumber", billNumber);
            bundle.putString("transactionProviderType", billType);
            bundle.putString("transactionProviderAmount", billAmount);
            bundle.putString("transactionPaymentMethod", billVia);

        }else{
            bundle.putBoolean("transactionStatus", false);
            bundle.putString("transactionProviderType", billType);
            bundle.putString("transactionError", transactionError);
        }
        Handler handler = new Handler();

        Runnable updateData = new Runnable() {
            @Override
            public void run() {
                fragmentStatus.setVisibility(View.VISIBLE);
                fragmentLoading.setVisibility(View.GONE);
                ft.replace(R.id.fragment_transaction_status, TransactionStatus.class, bundle)
                        .replace(R.id.fragment_nav_top, TopNav.class, navBundle).commit();
            }
        };
        handler.postDelayed(updateData,5000);

    }

}
