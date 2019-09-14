
package com.example.mankind.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mankind.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements OnChartValueSelectedListener {

    private PieChart pieChart;
    private String type;
    private PieChart pChart;
    private Spinner spinner;
    private Button info;
    private int position;
    private Spinner yearSpinner1;
    private Spinner yearSpinner2;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_notifications, container, false);
    pieChart = root.findViewById(R.id.pie_chart);
    pChart = root.findViewById(R.id.barchart);
    spinner = root.findViewById(R.id.spinner);
    yearSpinner1 = root.findViewById(R.id.spinner_year1);
    yearSpinner2 = root.findViewById(R.id.spinner_year2);
    info = root.findViewById(R.id.info_icon);
    position = 0;
    initYearSpinner();
    infoDisplay();
    initSpinner();
    initType();
    initPiechart();
    initBarchart();
    return root;
  }

    private void initYearSpinner() {
        final String[] spinnerItems = {"2017", "2016"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner1.setAdapter(spinnerAdapter);
        yearSpinner1.setSelection(0, true);
        yearSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    pieChart.notifyDataSetChanged();
                    initPiechart();
                }
                else{
                    pieChart.notifyDataSetChanged();
                    setCrimeData2016();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yearSpinner2.setAdapter(spinnerAdapter);
        yearSpinner2.setSelection(0, true);
        yearSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    pChart.notifyDataSetChanged();
                    initBarchart();
                }
                else{
                    pChart.notifyDataSetChanged();
                    initApproach2016();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCrimeData2016() {
        pieChart.setUsePercentValues(true);
        Description description = new Description();
        description.setText("Defendants of family violence offences finalised in the criminal courts");
        description.setTextSize(9.0f);
        pieChart.setDescription(description);

        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0);

        pieChart.setDrawCenterText(false);//

        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(60f);

        pieChart.setRotationEnabled(false);
        pieChart.setRotationAngle(10);

        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(R.color.black);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1000);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(42f, "Acquitted"));
        entries.add(new PieEntry(4050f, "Proven guilty"));
        entries.add(new PieEntry(312f, "Transfer to other court levels"));
        entries.add(new PieEntry(428f, "Withdrawn by prosecution"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors = setColor(colors);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    private void initApproach2016() {
        pChart.getDescription().setEnabled(false);
        pChart.setUsePercentValues(false);
        pChart.setExtraOffsets(5, 5, 5, 5);

        pChart.setDragDecelerationFrictionCoef(0);

        pChart.setCenterText("How people who experienced domestic violence seek for help");
        pChart.setDrawHoleEnabled(true);
        pChart.setHoleColor(Color.WHITE);

        pChart.setTransparentCircleColor(Color.WHITE);
        pChart.setTransparentCircleAlpha(110);

        pChart.setHoleRadius(58f);
        pChart.setTransparentCircleRadius(61f);
        pChart.setRotationEnabled(false);
        pChart.setDrawCenterText(true);

        pChart.setRotationAngle(10);

        pChart.getLegend().setEnabled(false);
        pChart.setHighlightPerTapEnabled(true);

        pChart.setDrawEntryLabels(true);
        pChart.setEntryLabelColor(R.color.black);
        pChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                PieEntry pe = (PieEntry)e;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage(((PieEntry) e).getValue() + " people seek help via " + pe.getLabel());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                pChart.highlightValue(null);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pChart.animateY(1000);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(26.1f, "General practitioner "));
        entries.add(new PieEntry(17.6f, "Other health professional"));
        entries.add(new PieEntry(7.5f, "Telephone helpline"));
        entries.add(new PieEntry(34f, "Police"));
        entries.add(new PieEntry(64.9f, "Friend or family member"));
        entries.add(new PieEntry(15.9f, "other resources"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors = setColors(colors);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);
        pChart.setData(pieData);
        pChart.highlightValues(null);
        pChart.invalidate();
    }

    private void infoDisplay() {
      info.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
              if(position == 0)
                dialogBuilder.setMessage("The chart shows defendants of domestic violence offences finalised in the criminal courts" + "\n" +
                        "\n" + "Data owned by Australian Institute Of Health And Welfare");
              else
                  dialogBuilder.setMessage("The chart shows how people experienced domestic violence seek advice or support" + "\n" +
                          "\n" + "Data owned by Australian Institute Of Health And Welfare");
              AlertDialog alertDialog = dialogBuilder.create();
              alertDialog.show();
          }
      });
    }

    private void initSpinner() {
        final String[] spinnerItems = {"The crime rate in domestic violence", "Approaches to seek help"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    yearSpinner2.setVisibility(View.VISIBLE);
                    yearSpinner1.setVisibility(View.GONE);
                    pieChart.setVisibility(View.GONE);
                    pChart.setVisibility(View.VISIBLE);
                    setPosition(1);
                } else {
                    pieChart.setVisibility(View.VISIBLE);
                    pChart.setVisibility(View.GONE);
                    yearSpinner1.setVisibility(View.VISIBLE);
                    yearSpinner2.setVisibility(View.GONE);
                    setPosition(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initBarchart() {
        pChart.getDescription().setEnabled(false);
        pChart.setUsePercentValues(false);
        pChart.setExtraOffsets(5, 5, 5, 5);
        pChart.setDragDecelerationFrictionCoef(0);
        pChart.setCenterText("How people seek help");
        pChart.setDrawHoleEnabled(true);
        pChart.setHoleColor(Color.WHITE);
        pChart.setTransparentCircleColor(Color.WHITE);
        pChart.setTransparentCircleAlpha(110);
        pChart.setHoleRadius(58f);
        pChart.setRotationEnabled(false);
        pChart.setTransparentCircleRadius(61f);
        pChart.setDrawCenterText(true);
        pChart.setRotationAngle(10);
        pChart.setHighlightPerTapEnabled(true);
        pChart.getLegend().setEnabled(false);
        pChart.setDrawEntryLabels(true);
        pChart.setEntryLabelColor(R.color.black);
        pChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                PieEntry pe = (PieEntry)e;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage(((PieEntry) e).getValue() + " people seek help via " + pe.getLabel());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                pChart.highlightValue(null);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pChart.animateY(1000);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(33.3f, "General practitioner "));
        entries.add(new PieEntry(20.1f, "Other health professional"));
        entries.add(new PieEntry(10.8f, "Telephone helpline"));
        entries.add(new PieEntry(16.7f, "Police"));
        entries.add(new PieEntry(66.9f, "Friend or family member"));
        entries.add(new PieEntry(9.3f, "other resources"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors = setColors(colors);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieData.setValueFormatter(new PercentFormatter(pChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);
        pChart.setData(pieData);
// undo all highlights
        pChart.highlightValues(null);
        pChart.invalidate();
    }


    private void initType() {
        SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        type = sp.getString("type", null);
    }

    private void initPiechart() {
        pieChart.setUsePercentValues(true);
        Description description = new Description();
        description.setText("Defendants of family violence offences finalised in the criminal courts");
        description.setTextSize(9.0f);
        pieChart.setDescription(description);
        pieChart.getLegend().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0);

        pieChart.setDrawCenterText(false);//

        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(60f);

        pieChart.setRotationEnabled(false);
        pieChart.setRotationAngle(10);

        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(R.color.black);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1000);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(450f, "Acquitted"));
        entries.add(new PieEntry(4073f, "Proven guilty"));
        entries.add(new PieEntry(111f, "Transfer to other court levels"));
        entries.add(new PieEntry(735f, "Withdrawn by prosecution"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors = setColor(colors);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart.setData(pieData);


        pieChart.invalidate();



    }

    private ArrayList<Integer> setColors(ArrayList<Integer> colors) {
        colors.add(Color.parseColor("#6B5B48"));
        colors.add(Color.parseColor("#F1A700"));
        colors.add(Color.parseColor("#EBC380"));
        colors.add(Color.parseColor("#8AA3CC"));
        colors.add(Color.parseColor("#f1e500"));
        colors.add(Color.parseColor("#f1c500"));
        return colors;
    }

    private ArrayList<Integer> setColor(ArrayList<Integer> colors) {
        colors.add(Color.parseColor("#EBC380"));
        colors.add(Color.parseColor("#8CC235"));
        colors.add(Color.parseColor("#F1A700"));
        colors.add(Color.parseColor("#8AA3CC"));
        return colors;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        PieEntry pe = (PieEntry)e;
        int cases = (int)pe.getValue();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(cases + " cases are " + pe.getLabel());
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        pieChart.highlightValue(null);
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onNothingSelected() {
        // undo all highlights
        pieChart.highlightValues(null);
        Log.i("PieChart", "nothing selected");
    }
}