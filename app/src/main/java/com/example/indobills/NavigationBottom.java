package com.example.indobills;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationBottom extends Fragment {

    private TextView tvLeftMenu, tvRightMenu;
    private View view;
    Context currentContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation_bottom, container, false);
        tvLeftMenu = view.findViewById(R.id.left_nav);
        tvRightMenu = view.findViewById(R.id.log_out);

        String navMenu = getArguments().getString("navMenu");
        Boolean onProcessed = getArguments().getBoolean("onProcess");

        if(onProcessed){
            tvLeftMenu.setVisibility(View.GONE);
            tvRightMenu.setVisibility(View.GONE);
        }else{
            tvLeftMenu.setVisibility(View.VISIBLE);
            tvRightMenu.setVisibility(View.VISIBLE);
        }

        currentContext = container.getContext();

        tvRightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = currentContext.getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
                sp.edit().clear().commit();

                Intent intent = new Intent(currentContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        tvLeftMenu.setText(navMenu);

        tvLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                if(navMenu.contains("Home")){
                    intent = new Intent(currentContext, HomeActivity.class);
                }else{
                    intent = new Intent(currentContext, TransactionListActivity.class);
                }

                startActivity(intent);

            }
        });

        return view;
    }
}