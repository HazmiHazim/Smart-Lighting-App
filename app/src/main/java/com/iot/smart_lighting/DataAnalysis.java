package com.iot.smart_lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.smart_lighting.Adapter.DataAnalysisAdapter;

public class DataAnalysis extends AppCompatActivity {

    ImageView back, setting, emptyDataIcon;

    // Declare adapter instance
    DataAnalysisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analysis);

        back = findViewById(R.id.back_btn4);
        setting = findViewById(R.id.setting_btn5);
        emptyDataIcon = findViewById(R.id.noDeviceConnected);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataAnalysis.this, Main.class);
                startActivity(intent);
            }
        });

        //setRecyclerview();
    }

    // Method to fetch data from sql to recyclerview
    private void setRecyclerview() {
        // Initialize adapter instance
        adapter = new DataAnalysisAdapter();
        // Query data
    }
}
