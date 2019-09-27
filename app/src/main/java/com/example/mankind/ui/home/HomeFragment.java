package com.example.mankind.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mankind.MyApplication;
import com.example.mankind.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    //violence type
    private String type;
    private TextView textView;
    private TextView org1;
    private TextView link1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.home_title);
        org1 = root.findViewById(R.id.org1);
        link1 = root.findViewById(R.id.link1);
        initType();
        init();

        return root;
    }

    private void initType() {
        type = ((MyApplication)getActivity().getApplication()).getType();
    }

    private void init() {
        String title = "Websites to get rid of " + type + " abuse";
        if(type == null){
            textView.setText("No Data");
            org1.setText("");
            link1.setText("");
        }
        else{
            textView.setText(title);
            org1.setText("White Ribbon Australia");
            if(type.equals("physical"))
                link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/physical-abuse/");
            if(type.equals("financial"))
                link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/financial-abuse/");
            if(type.equals("emotional"))
                link1.setText("https://www.whiteribbon.org.au/understand-domestic-violence/types-of-abuse/emotional-abuse/");
        }
    }
}