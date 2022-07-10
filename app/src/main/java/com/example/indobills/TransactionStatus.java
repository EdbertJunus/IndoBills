package com.example.indobills;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.indobills.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class TransactionStatus extends Fragment{


    private TextView tvTitle, tvMessage, tvDetailName, tvDetailNumber, tvDetailAmount, tvDetailMethod;
    private LinearLayout linearLayoutSuccess, linearLayoutFail, linearLayoutMap;
    private View view;
    private String transactionProviderName, transactionProviderNumber,
            transactionProviderType, transactionProviderAmount, transactionPaymentMethod, transactionError;
    private Boolean transactionStatus;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_status, container, false);
        ctx = container.getContext();

        //Arguments Bundle
        transactionStatus = getArguments().getBoolean("transactionStatus");
        transactionProviderType = getArguments().getString("transactionProviderType");

        if(transactionStatus){
            transactionProviderName = getArguments().getString("transactionProviderName");
            transactionProviderNumber = getArguments().getString("transactionProviderNumber");
            transactionProviderAmount = getArguments().getString("transactionProviderAmount");
            transactionPaymentMethod = getArguments().getString("transactionPaymentMethod");
        }else{
            transactionError = getArguments().getString("transactionError");
        }

        //View
        linearLayoutSuccess = view.findViewById(R.id.ll_transaction_success);
        linearLayoutFail = view.findViewById(R.id.ll_transaction_fail);
        tvTitle = view.findViewById(R.id.tv_transaction_title);
        tvMessage = view.findViewById(R.id.tv_transaction_msg);
        tvDetailName = view.findViewById(R.id.tv_transaction_detail_name);
        tvDetailNumber = view.findViewById(R.id.tv_transaction_detail_number);
        tvDetailAmount = view.findViewById(R.id.tv_transaction_detail_amount);
        tvDetailMethod = view.findViewById(R.id.tv_transaction_detail_via);

        //Status Success
        if(transactionStatus){
            linearLayoutSuccess.setVisibility(View.VISIBLE);
            tvTitle.setText("SUCCESS!");
            tvMessage.setText("Your transaction number is "+transactionProviderType.substring(0,1) + transactionProviderNumber);
            tvDetailName.setText(transactionProviderType + " Provider: "+transactionProviderName);
            tvDetailNumber.setText(transactionProviderType + " Number: "+transactionProviderNumber);

            DecimalFormat indonesiaRp = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols rpFormat = new DecimalFormatSymbols();

            rpFormat.setCurrencySymbol("Rp. ");
            rpFormat.setGroupingSeparator(',');

            indonesiaRp.setDecimalFormatSymbols(rpFormat);

            String formattedPrice = indonesiaRp.format(Integer.parseInt(transactionProviderAmount));
            formattedPrice = formattedPrice.substring(0, formattedPrice.length()-3);

            tvDetailAmount.setText("Amount: "+formattedPrice+",-");
            tvDetailMethod.setText("Via: "+transactionPaymentMethod);
        }else{
            linearLayoutFail.setVisibility(View.VISIBLE);
            tvTitle.setText("FAILED!");
            tvMessage.setText(transactionProviderType+ " "+transactionError+ " Can't be found");
        }

        return view;
    }
}