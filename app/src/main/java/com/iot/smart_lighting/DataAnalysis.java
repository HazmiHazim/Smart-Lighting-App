package com.iot.smart_lighting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
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

    ImageView back, setting;
    LineChart lineChart;
    List<String> xValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analysis);

        back = findViewById(R.id.back_btn4);
        setting = findViewById(R.id.setting_btn5);
        lineChart = findViewById(R.id.line_chart);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataAnalysis.this, Main.class);
                startActivity(intent);
            }
        });

        Description description = new Description();
        description.setText("Voltage (V)");
        description.setPosition(150f, 15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("Lamp 1", "Lamp 2", "Lamp 3");

        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setValueFormatter(new IndexAxisValueFormatter(xValues));
        x.setLabelCount(4);
        x.setGranularity(1f);

        YAxis y = lineChart.getAxisLeft();
        y.setAxisMinimum(0f);
        y.setAxisMaximum(100f);
        y.setAxisLineWidth(2f);
        y.setLabelCount(10);

        // Dummy data for lamp 1
        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 10f));
        entries1.add(new Entry(1, 10f));
        entries1.add(new Entry(2, 15f));
        entries1.add(new Entry(3, 45f));

        // Dummy data for lamp 2
        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0, 80f));
        entries2.add(new Entry(1, 60f));
        entries2.add(new Entry(2, 50f));
        entries2.add(new Entry(3, 30f));

        // Dummy data for lamp 3
        List<Entry> entries3 = new ArrayList<>();
        entries3.add(new Entry(0, 35));
        entries3.add(new Entry(1, 70f));
        entries3.add(new Entry(2, 40f));
        entries3.add(new Entry(3, 25f));

        LineDataSet dataSet1 = new LineDataSet(entries1, "Lamp 1");
        dataSet1.setColor(Color.GREEN);

        LineDataSet dataSet2 = new LineDataSet(entries2, "Lamp 2");
        dataSet2.setColor(Color.BLUE);

        LineDataSet dataSet3 = new LineDataSet(entries3, "Lamp 3");
        dataSet3.setColor(Color.RED);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
