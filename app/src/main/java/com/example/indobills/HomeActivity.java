package com.example.indobills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

public class HomeActivity extends AppCompatActivity {

    private FragmentContainerView wifiBill, waterBill, phoneBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putString("navMenu", "Transaction List");
            bundle.putBoolean("navBack", false);

            Bundle wifiBundle = new Bundle();
            wifiBundle.putString("item_text",  getResources().getString(R.string.bill_pay_wifi));

            Bundle phoneBundle = new Bundle();
            phoneBundle.putString("item_text",  getResources().getString(R.string.bill_pay_phone));

            Bundle waterBundle = new Bundle();
            waterBundle.putString("item_text",  getResources().getString(R.string.bill_pay_water));

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_nav_bottom, NavigationBottom.class, bundle)
                    .add(R.id.fragment_nav_top, TopNav.class, bundle)
                    .add(R.id.fragment_wifi_item, BillItem.class, wifiBundle)
                    .add(R.id.fragment_phone_item, BillItem.class, phoneBundle)
                    .add(R.id.fragment_water_item, BillItem.class, waterBundle)
                    .commit();
        }

        init();
    }

    private void init(){
        wifiBill = findViewById(R.id.fragment_wifi_item);
        phoneBill = findViewById(R.id.fragment_phone_item);
        waterBill = findViewById(R.id.fragment_water_item);

        wifiBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BillsDetailActivity.class);
                intent.putExtra("billType", getResources().getString(R.string.bill_pay_wifi));

                startActivity(intent);
            }
        });

        phoneBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BillsDetailActivity.class);
                intent.putExtra("billType",  getResources().getString(R.string.bill_pay_phone));
                startActivity(intent);
            }
        });

        waterBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BillsDetailActivity.class);
                intent.putExtra("billType", getResources().getString(R.string.bill_pay_water));
                startActivity(intent);
            }
        });
    }
}
