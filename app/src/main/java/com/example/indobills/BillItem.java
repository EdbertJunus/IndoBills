package com.example.indobills;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BillItem extends Fragment {

    private TextView tvBills;
    private ImageView ivBills;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bill_item, container, false);
        tvBills = view.findViewById(R.id.tv_bills);
        ivBills = view.findViewById(R.id.iv_bills);
        String tvText = getArguments().getString("item_text");

        tvBills.setText(tvText);
        if(tvText.toLowerCase().contains("phone")){
            ivBills.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_local_phone_24));
        }

        return view;
    }
}