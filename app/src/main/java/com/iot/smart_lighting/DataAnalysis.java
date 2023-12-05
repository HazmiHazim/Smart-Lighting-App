package com.iot.smart_lighting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataAnalysis extends AppCompatActivity {

    ImageView back, info;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analysis);

        back = findViewById(R.id.back_btn4);
        info = findViewById(R.id.info_btn5);
        lineChart = findViewById(R.id.line_chart);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBox = new AlertDialog.Builder(DataAnalysis.this);
                infoBox.setIcon(R.drawable.phoenix);
                infoBox.setTitle("Data Analysis Commands");
                infoBox.setMessage("Supported Commands:" +
                        "\nOpen Data Analysis" +
                        "\nShow Data Analysis");
                AlertDialog alertDialog = infoBox.create();
                alertDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Styling Line Chart
        lineChart.getDescription().setEnabled(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setBackgroundColor(Color.parseColor("#000000"));

        // Disable X Axis
        XAxis x = lineChart.getXAxis();
        x.setEnabled(false);
        x.setGranularityEnabled(true);
        x.setDrawAxisLine(true);
        x.setTextColor(Color.parseColor("#F5F5F5"));

        // Y Axis on the Left Side
        YAxis yLeft = lineChart.getAxisLeft();
        yLeft.setEnabled(true);
        yLeft.setDrawAxisLine(true);
        yLeft.setDrawLabels(true);
        yLeft.setGranularityEnabled(true);
        yLeft.setTextColor(Color.parseColor("#F5F5F5"));

        // Y Axis on the Right Side
        YAxis yRight = lineChart.getAxisRight();
        yRight.setEnabled(false);

        // Dummy data for Entry 1
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0f));
        entries.add(new Entry(1, 21f));
        entries.add(new Entry(2, 23));
        entries.add(new Entry(3, 25f));
        entries.add(new Entry(4, 27f));
        entries.add(new Entry(5, 15f));
        entries.add(new Entry(6, 18f));
        entries.add(new Entry(7, 27f));
        entries.add(new Entry(8, 23f));
        entries.add(new Entry(9, 25f));
        entries.add(new Entry(10, 0f));


        LineDataSet dataSet = new LineDataSet(entries, "Current Flow (Ampere)");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#87CEEB"));
        dataSet.setFillAlpha(1000);
        dataSet.setLineWidth(2);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.parseColor("#F5F5F5"));
        legend.setForm(Legend.LegendForm.CIRCLE);
    }
}
