package com.example.mankind.ui.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mankind.DateUtil;
import com.example.mankind.Entity.Record;
import com.example.mankind.LineChartMarkView;
import com.example.mankind.Question1Activity;
import com.example.mankind.Question2_1Activity;
import com.example.mankind.R;
import com.example.mankind.RecordAdapter;
import com.example.mankind.Records_Config;
import com.example.mankind.db.DBFacade;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.liang.jtablayout.tab.Tab;
import com.liang.widget.JTabLayout;

import java.util.ArrayList;
import java.util.Collections;
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
    //Tab layout
    private JTabLayout tabLayout;
    //LinearLayout for self assessment
    private LinearLayout self_assessment;
    //LinearLayout for check_situation
    private LinearLayout check_stituation;

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
        initTab(root);
        return root;
    }

    //init tab
    private void initTab(View root) {
        self_assessment = root.findViewById(R.id.self_assessment);
        check_stituation = root.findViewById(R.id.check_type);
        tabLayout = root.findViewById(R.id.tabLayout1);
        tabLayout.addTab(tabLayout.newTab().setTitle("Self-Assessment"));
        tabLayout.addTab(tabLayout.newTab().setTitle("Check My Situation"));
        tabLayout.addOnTabSelectedListener(new JTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull Tab var1) {
                if(var1.getPosition() == 0){
                    self_assessment.setVisibility(View.VISIBLE);
                    check_stituation.setVisibility(View.GONE);
                }
                else{
                    self_assessment.setVisibility(View.GONE);
                    check_stituation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(@NonNull Tab var1) {

            }

            @Override
            public void onTabReselected(@NonNull Tab var1) {

            }
        });
    }

    //init info icon
    private void initInfo(View root) {
        Button info = root.findViewById(R.id.info_icon);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("In self tracker your history records are displayed." + "\n" +
                        "\n" + "You can choose to view them in table or you can visualize them using a line chart.");
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
        mLineChar.setOnChartValueSelectedListener(null);
        mLineChar.setDrawGridBackground(false);
        mLineChar.setDrawBorders(false);
        mLineChar.setDragEnabled(false);
        mLineChar.setScaleEnabled(false);
        mLineChar.setDoubleTapToZoomEnabled(false);
        mLineChar.setTouchEnabled(true);
        XAxis xAxis = mLineChar.getXAxis();
        YAxis leftYAxis = mLineChar.getAxisLeft();
        YAxis rightYAxis = mLineChar.getAxisRight();
        rightYAxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        leftYAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(5,false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String date = records.get((int) value % records.size()).getTime();
                return DateUtil.formatDate(date);
            }
        });
        leftYAxis.setDrawGridLines(true);
        Legend legend = mLineChar.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        mLineChar.getDescription().setEnabled(false);
        leftYAxis.setAxisMaximum(110);
        mLineChar.setBackgroundColor(getResources().getColor(R.color.white));
        showLineChart("My History Records", getResources().getColor(R.color.blue_select));
        LineChartMarkView mv = new LineChartMarkView(getActivity(), xAxis.getValueFormatter());
        mv.setChartView(mLineChar);
        mLineChar.setMarker(mv);
        mLineChar.invalidate();

    }

    //fill the linechart and set color
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    //add data entry
    private void showLineChart(String name, int color) {
        ArrayList<Record> lineRecord;
        if(records.size()>10){
            lineRecord = new ArrayList<>(records.subList(records.size()-10,records.size()));
        }
        else
            lineRecord = new ArrayList<>(records);
        Collections.reverse(lineRecord);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < lineRecord.size(); i++) {
            Record data = lineRecord.get(i);
            Entry entry = new Entry(i, (float)data.getResult());
            entries.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        mLineChar.setData(lineData);
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
                    mLineChar.animateY(1500);
                    mLineChar.animateX(1500);
                    mLineChar.invalidate();
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
