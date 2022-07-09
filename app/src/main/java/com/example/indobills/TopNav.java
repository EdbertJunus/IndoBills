package com.example.indobills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopNav extends Fragment {

    private TextView tvBack;
    private View view;
    private Boolean navBack, backToHome;
    Context currentContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top_nav, container, false);
        tvBack = view.findViewById(R.id.back_btn);
        navBack = getArguments().getBoolean("navBack");
        backToHome = getArguments().getBoolean("backToHome");

        if(navBack){
            tvBack.setVisibility(View.VISIBLE);
        }else{
            tvBack.setVisibility(View.INVISIBLE);
        }

        currentContext = container.getContext();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backToHome){
                    Intent intent = new Intent(currentContext, HomeActivity.class);
                    startActivity(intent);
                }
                ((Activity) currentContext).finish();
            }
        });

        return view;
    }
}