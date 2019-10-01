package com.example.mankind;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * The type Line chart mark view.
 */
public class LineChartMarkView extends MarkerView {

    private TextView tvDate;
    private TextView tvValue;
    private ValueFormatter xAxisValueFormatter;

    /**
     * Instantiates a new Line chart mark view.
     *
     * @param context             the context
     * @param xAxisValueFormatter the x axis value formatter
     */
    public LineChartMarkView(Context context, ValueFormatter xAxisValueFormatter) {
        super(context, R.layout.marker_view);
        this.xAxisValueFormatter = xAxisValueFormatter;

        tvDate = findViewById(R.id.tv_date);
        tvValue = findViewById(R.id.tv_value);
    }

    //generate customized x,y value
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvDate.setText(xAxisValueFormatter.getAxisLabel(e.getX(), null));
        tvValue.setText("Result：" + (int)e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}