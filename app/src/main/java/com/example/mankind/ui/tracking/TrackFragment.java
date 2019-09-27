package com.example.mankind.ui.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mankind.Entity.Record;
import com.example.mankind.Question1Activity;
import com.example.mankind.Question2_1Activity;
import com.example.mankind.R;
import com.example.mankind.RecordAdapter;
import com.example.mankind.Records_Config;
import com.example.mankind.db.DBFacade;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Track fragment.
 */
public class TrackFragment extends Fragment {
    //history records
    private List<Record> records;
    //line chart
    private LineChart mLineChar;
    //recycler view to conatin records
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tracker,container,false);
        initRecords();
        initLineChart(root);
        initRecyclerView(root);
        initButton(root);
        initSpinner(root);
        initInfo(root);
        return root;
    }

    //init info icon
    private void initInfo(View root) {
        Button info = root.findViewById(R.id.info_icon);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("In self tracker your history records are displayed." + "\n" +
                        "\n" + "You can choose to view them in table or you can visualize them using a line chart");
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    //init recycler view
    private void initRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.recycler_view);
        RecordAdapter recordAdapter = new RecordAdapter(getActivity(), records);
        new Records_Config().setConfig(recyclerView, getActivity(),recordAdapter);
    }

    //init line chart
    private void initLineChart(View root) {
        mLineChar = root.findViewById(R.id.line_chart);
    }

    //init spinner
    private void initSpinner(View root) {
        Spinner spinner = root.findViewById(R.id.spinner);
        final String[] spinnerItems = {"History Records", "Line chart"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    mLineChar.setVisibility(View.GONE);
                }
                if(position == 1){
                    recyclerView.setVisibility(View.GONE);
                    mLineChar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //init records
    private void initRecords() {
        DBFacade dbFacade = DBFacade.getInstance();
        dbFacade.init(getActivity().getApplicationContext());
        records = dbFacade.getAllRecord();
    }

    //init button
    private void initButton(View root) {
        Button redo1 = root.findViewById(R.id.redo1);
        redo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Question1Activity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        Button redo2 = root.findViewById(R.id.redo2);
        redo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Question2_1Activity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
    }
}
