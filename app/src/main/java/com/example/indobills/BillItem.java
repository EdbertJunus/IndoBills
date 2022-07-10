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
            ivBills.setImageResource(R.mipmap.phone_border_foreground);
        }else if(tvText.toLowerCase().contains("water")){
            ivBills.setImageResource(R.mipmap.water_drop_foreground);
        }

        return view;
    }
}