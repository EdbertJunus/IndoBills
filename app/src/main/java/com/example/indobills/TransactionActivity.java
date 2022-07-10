package com.example.indobills;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.indobills.databinding.ActivityMapsBinding;
import com.example.indobills.model.Bill;
import com.example.indobills.model.BillHelper;
import com.example.indobills.model.Transaction;
import com.example.indobills.model.TransactionHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String textBills, billName, billType, billVia, billNumber, billAmount, transactionError, BillId = "", UserId, TransactionId;
    private Date TransactionDate;
    private TransactionHelper transactionHelper;
    private BillHelper billHelper;

    private Boolean billNumIsCorrect = false;
    private Boolean transactionStatus = true;

    private TextView tvTitle, tvMsg, tvBillName, tvBillNumber, tvBillAmount, tvBillVia;
    private LinearLayout layoutSuccess;
    private FragmentContainerView fragmentLoading, fragmentStatus;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    public void onBackPressed() {
        return;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        textBills = getIntent().getStringExtra("billType");

        billName = getIntent().getStringExtra("inputBillName");
        billNumber = getIntent().getStringExtra("inputBillNumber");
        billType = getIntent().getStringExtra("inputBillType");
        billAmount = getIntent().getStringExtra("inputBillAmount");
        billVia = getIntent().getStringExtra("inputBillVia");

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString("navMenu", "Home");
            bundle.putBoolean("navBack", false);
            bundle.putBoolean("onProcess", true);

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

    private void init() {
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

        //Bill Id If False as initialize
        BillId = billName + "#" + billNumber + "#" + billType;

        for (Bill bill : billArrayList) {
            if (bill.getBillProviderNumber().equals(billNumber)) {
                billNumIsCorrect = true;
                BillId = bill.getBillId();
            }
        }

        if (billArrayList.size() == 0) {
            //Provider Name Wrong
            transactionStatus = false;
            transactionError = "Name";

        } else if (!billNumIsCorrect) {
            //Provider Number Wrong
            transactionStatus = false;
            transactionError = "Number";
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Bundle navBundle = new Bundle();
        navBundle.putBoolean("navBack", true);
        navBundle.putBoolean("backToHome", true);
        navBundle.putBoolean("onProcess", false);
        navBundle.putString("navMenu", "Home");

        UserId = getSharedPreferences("loginStatus", Context.MODE_PRIVATE).getString("UserId", "");
        TransactionDate = new Date();

        if (transactionStatus) {
            bundle.putBoolean("transactionStatus", true);
            bundle.putString("transactionProviderName", billName);
            bundle.putString("transactionProviderNumber", billNumber);
            bundle.putString("transactionProviderType", billType);
            bundle.putString("transactionProviderAmount", billAmount);
            bundle.putString("transactionPaymentMethod", billVia);

        } else {
            bundle.putBoolean("transactionStatus", false);
            bundle.putString("transactionProviderType", billType);
            bundle.putString("transactionError", transactionError);
        }

        transactionHelper = new TransactionHelper(TransactionActivity.this);
        transactionHelper.open();
        Transaction transaction = new Transaction("", TransactionDate, BillId, billAmount, billVia, transactionStatus, UserId);
        transactionHelper.insertTransaction(transaction);
        transactionHelper.close();

        Handler handler = new Handler();

        Runnable updateData = new Runnable() {
            @Override
            public void run() {
                fragmentStatus.setVisibility(View.VISIBLE);
                fragmentLoading.setVisibility(View.GONE);
                ft.replace(R.id.fragment_transaction_status, TransactionStatus.class, bundle)
                        .replace(R.id.fragment_nav_top, TopNav.class, navBundle)
                        .replace(R.id.fragment_nav_bottom, NavigationBottom.class, navBundle)
                        .commit();

                if (transactionStatus) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("notification1", "SuccessTransaction", NotificationManager.IMPORTANCE_HIGH);
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            TransactionActivity.this, "notification1"
                    )
                            .setSmallIcon(R.drawable.ic_baseline_info_24)
                            .setContentTitle("Your Billing is Successfully Paid!")
                            .setContentText("You can check on the IndoBills's transaction list")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(
                            TransactionActivity.this);
                    notificationManagerCompat.notify(0, builder.build());
                }

                SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.map_transaction, mapFragment)
                        .commit();

                mapFragment.getMapAsync(TransactionActivity.this);

            }
        };
        handler.postDelayed(updateData, 2000);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng indobills = new LatLng(-6.191209804102217, 106.78212029924254);
        mMap.addMarker(new MarkerOptions().position(indobills).title("Customer Service Indobills"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indobills));
        mMap.getUiSettings().setScrollGesturesEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(TransactionActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
