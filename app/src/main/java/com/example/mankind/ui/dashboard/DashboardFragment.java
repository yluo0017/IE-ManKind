package com.example.mankind.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mankind.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DashboardFragment extends Fragment {

    private Spinner spinner;
    private int week;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        spinner = root.findViewById(R.id.spinner);
        initSpinner();
        initTasks();
        return root;
    }

    private void initTasks() {
    }

    private void initSpinner() {
        week = 1;
        final String[] spinnerItems = {"1 week", "3 weeks", "5 weeks"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    week = 1;
                }
                if(position == 1){
                    week = 3;
                }
                if(position == 2){
                    week = 5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class loadAsyncTask extends AsyncTask<Object, Object, Object>{

        @Override
        protected Object doInBackground(Object... objects) {
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL("http://35.201.13.155/projects/My Project 89479/instances/man-kind/databases/mankind");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;

        }
    }

    private class MyAsyncTask extends AsyncTask<Object, Object, Object>{

        @Override
        protected Object doInBackground(Object... objects) {

            return null;
        }
    }
}