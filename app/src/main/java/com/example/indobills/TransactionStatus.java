package com.example.indobills;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TransactionStatus extends Fragment {

    private TextView tvTitle, tvMessage, tvDetailName, tvDetailNumber, tvDetailAmount, tvDetailMethod;
    private LinearLayout linearLayoutSuccess, linearLayoutFail;
    private View view;
    Context currentContext;
    private String transactionId, transactionProviderName, transactionProviderNumber,
            transactionProviderType, transactionProviderAmount, transactionPaymentMethod, transactionError;
    private Boolean transactionStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_status, container, false);

        //Arguments Bundle
        transactionStatus = getArguments().getBoolean("transactionStatus");
        transactionProviderType = getArguments().getString("transactionProviderType");

        if(transactionStatus){
            transactionId = getArguments().getString("transactionId");
            transactionProviderName = getArguments().getString("transactionProviderName");
            transactionProviderNumber = getArguments().getString("transactionProviderNumber");
            transactionProviderAmount = getArguments().getString("transactionProviderAmount");
            transactionPaymentMethod = getArguments().getString("transactionPaymentMethod");
        }else{
            transactionError = getArguments().getString("transactionError");
        }

        transactionId = getArguments().getString("transactionId");
        transactionProviderName = getArguments().getString("transactionProviderName");
        transactionProviderNumber = getArguments().getString("transactionProviderNumber");

        transactionProviderAmount = getArguments().getString("transactionProviderAmount");
        transactionPaymentMethod = getArguments().getString("transactionPaymentMethod");

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
            tvMessage.setText("Your transaction number is W12345");
            tvDetailName.setText(transactionProviderType + " Provider: "+transactionProviderName);
            tvDetailNumber.setText(transactionProviderType + " Number: "+transactionProviderNumber);
            tvDetailAmount.setText("Amount: "+transactionProviderAmount);
            tvDetailMethod.setText("Via: "+transactionPaymentMethod);
        }else{
            linearLayoutFail.setVisibility(View.VISIBLE);
            tvTitle.setText("FAILED!");
            tvMessage.setText(transactionProviderType+ " "+transactionError+ " Can't be found");
        }


        return view;
    }
}