package com.example.indobills;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        currentContext = container.getContext();

        tvRightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentContext, MainActivity.class);
                startActivity(intent);
            }
        });

        tvLeftMenu.setText(navMenu);

        return view;
    }
}