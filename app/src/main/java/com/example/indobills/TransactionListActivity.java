package com.example.indobills;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indobills.adapter.TransactionListAdapter;
import com.example.indobills.model.Transaction;
import com.example.indobills.model.TransactionHelper;

import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity {

    private RecyclerView rvTransactionList;
    private ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    private TransactionHelper transactionHelper;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("navMenu", "Home");
            bundle.putBoolean("navBack", true);


            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_nav_bottom, NavigationBottom.class, bundle)
                    .add(R.id.fragment_nav_top, TopNav.class, bundle)
                    .commit();
        }

        init();
    }

    private void init(){
        UserId = getSharedPreferences("loginStatus", Context.MODE_PRIVATE).getString("UserId","");
        transactionHelper = new TransactionHelper(TransactionListActivity.this);
        transactionHelper.open();
        transactionArrayList = transactionHelper.viewTransactionByUserId(UserId);
        transactionHelper.close();

        rvTransactionList = findViewById(R.id.rv_transaction_list);

        rvTransactionList.setAdapter(new TransactionListAdapter(this, transactionArrayList));
        rvTransactionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }
}
