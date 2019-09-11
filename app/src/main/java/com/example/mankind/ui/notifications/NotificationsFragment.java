
package com.example.mankind.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mankind.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationsFragment extends Fragment implements OnChartValueSelectedListener {

  private NotificationsViewModel notificationsViewModel;
    private PieChart pieChart;
    private Switch aSwitch;
    private String type;
    private LineChart lineChart;
    private TextView description;
    private final RectF onValueSelectedRectF = new RectF();

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel.class);
    View root = inflater.inflate(R.layout.fragment_notifications, container, false);
    pieChart = root.findViewById(R.id.pie_chart);
    aSwitch = root.findViewById(R.id.switch1);
    lineChart = root.findViewById(R.id.barchart);
    description = root.findViewById(R.id.description);
    switchListen();
    initType();
    initPiechart();
    initBarchart();
    return root;
  }

    private void initBarchart() {

      lineChart.getDescription().setEnabled(false);
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(2012, 1720));
        entries.add(new Entry(2013, 1703));
        entries.add(new Entry(2014, 1689));
        entries.add(new Entry(2015, 1646));
        entries.add(new Entry(2016, 1537));
        entries.add(new Entry(2017, 1480));
        LineDataSet dataSet = new LineDataSet(entries, "year"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#ff5500"));
        dataSet.setCircleColor(Color.parseColor("#ff5500"));
        dataSet.setLineWidth(1f);
        ArrayList<String> xlist = new ArrayList<>();
        xlist.add("2012");
        xlist.add("2013");
        xlist.add("2014");
        xlist.add("2015");
        xlist.add("2016");
        xlist.add("2017");
        lineChart.getAxisLeft().setAxisMinimum(0);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setLabelCount(entries.size(), true);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate(); // refresh

        lineChart.animateY(2000);
    }

    private void switchListen() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pieChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                }else {
                    pieChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initType() {
        SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        type = sp.getString("type", null);
    }

    private void initPiechart() {
        pieChart.setUsePercentValues(true);
        Description description = new Description();
        description.setText("Impact of violence on health condition");
        description.setTextSize(12.0f);
        pieChart.setDescription(description);//设置描述

        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawCenterText(false);//

        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(60f);

        pieChart.setRotationEnabled(true);
        pieChart.setRotationAngle(10);

        pieChart.setHighlightPerTapEnabled(true);

        Legend l = pieChart.getLegend();
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

// entry label styling
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(Color.BLACK);
//pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(6f);

        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1000);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(37f, "Depression"));
        entries.add(new PieEntry(23f, "Anxiety"));
        entries.add(new PieEntry(12f, "Suicide"));
        entries.add(new PieEntry(10f, "Alcohol"));
        entries.add(new PieEntry(10f, "Tobacco"));
        entries.add(new PieEntry(5f, "Drug use"));
        entries.add(new PieEntry(11f, "Others"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        setColor(colors);
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart.setData(pieData);
// undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    private void setColor(List<Integer> colors) {
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}