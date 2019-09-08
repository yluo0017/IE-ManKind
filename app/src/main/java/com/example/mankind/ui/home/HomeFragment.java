package com.example.mankind.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mankind.R;

import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String type;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        type = sp.getString("type", null);
        final TextView textView = root.findViewById(R.id.home_title);
        final TextView org1 = root.findViewById(R.id.org1);
        final TextView link1 = root.findViewById(R.id.link1);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(type == null)
                    return;
                String title = "Websites to get rid of " + type + " abuse";
                textView.setText(title);
                org1.setText("White Ribbon Australia");
                if(type.equals("physical"))
                    link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/physical-abuse/");
                if(type.equals("financial"))
                    link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/financial-abuse/");
                if(type.equals("emotional"))
                    link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/emotional-abuse/");
            }
        });
        return root;
    }
}