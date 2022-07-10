package com.example.indobills;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TransactionDetailActivity extends AppCompatActivity {

    private String code, name, number, amount, method, type;
    private Boolean status;
    private TextView tvCard, tvName, tvNumber, tvAmount, tvMethod, tvStatus;
    private ImageView ivCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
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
        code = getIntent().getStringExtra("transactionCode");
        name = getIntent().getStringExtra("providerName");
        number = getIntent().getStringExtra("providerNumber");
        amount = getIntent().getStringExtra("providerAmount");
        method = getIntent().getStringExtra("providerMethod");
        type = getIntent().getStringExtra("providerType");
        status = getIntent().getBooleanExtra("transactionStatus", false);

        tvCard = findViewById(R.id.tv_transaction_card_detail);
        tvName = findViewById(R.id.tv_transaction_detail_name);
        tvNumber = findViewById(R.id.tv_transaction_detail_number);
        tvAmount = findViewById(R.id.tv_transaction_detail_amount);
        tvMethod = findViewById(R.id.tv_transaction_detail_via);
        tvStatus = findViewById(R.id.tv_transaction_status);
        ivCard = findViewById(R.id.iv_transaction_card);

        tvCard.setText(code);
        tvName.setText(type+" Provider: "+name);
        tvNumber.setText(type+" Number: "+number);

        DecimalFormat indonesiaRp = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols rpFormat = new DecimalFormatSymbols();

        rpFormat.setCurrencySymbol("Rp. ");
        rpFormat.setGroupingSeparator(',');

        indonesiaRp.setDecimalFormatSymbols(rpFormat);

        String formattedPrice = indonesiaRp.format(Integer.parseInt(amount));
        formattedPrice = formattedPrice.substring(0, formattedPrice.length()-3);

        tvAmount.setText("Amount: "+formattedPrice+",-");
        tvMethod.setText("Via: "+method);

        if(type.toLowerCase().contains("phone")){
            ivCard.setImageResource(R.mipmap.phone_border_foreground);
        }else if(type.toLowerCase().contains("water")){
            ivCard.setImageResource(R.mipmap.water_drop_foreground);
        }

        String statusMessage;
        if(status){
            statusMessage="Status: Success";
        }else{
            statusMessage="Status: Fail";
        }

        SpannableString content = new SpannableString(statusMessage);
        content.setSpan(new UnderlineSpan(), 0, statusMessage.length(), 0);
        tvStatus.setText(content);

    }

}
