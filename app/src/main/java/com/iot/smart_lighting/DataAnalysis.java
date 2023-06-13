package com.iot.smart_lighting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.smart_lighting.Adapter.DataAnalysisAdapter;
import com.iot.smart_lighting.Adapter.TimerAdapter;
import com.iot.smart_lighting.Model.SmartLampDB;

import java.util.ArrayList;

public class DataAnalysis extends AppCompatActivity {

    ImageView back, setting, emptyDataIcon;
    RecyclerView recyclerView;

    // Declare adapter instance
    DataAnalysisAdapter adapter;

    SmartLampDB myDB;
    ArrayList <Integer> id, ssid_name, status, intensity;
    ArrayList <String> colour;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analysis);

        back = findViewById(R.id.back_btn4);
        setting = findViewById(R.id.setting_btn5);
        emptyDataIcon = findViewById(R.id.noDeviceConnected);
        recyclerView = findViewById(R.id.recyclerViewDataAnalysis);

        // Create instance for SmartLampDB
        myDB = new SmartLampDB(DataAnalysis.this);

        id = new ArrayList<Integer>();
        ssid_name = new ArrayList<Integer>();
        status = new ArrayList<Integer>();
        intensity = new ArrayList<Integer>();
        colour = new ArrayList<String>();

        read();

        // Attach DataAnalysisAdapter to this class
        adapter = new DataAnalysisAdapter(DataAnalysis.this, id, ssid_name, status, intensity, colour);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataAnalysis.this));
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataAnalysis.this, Main.class);
                startActivity(intent);
            }
        });

    }

    // Fetch data from SmartLamp db
    private void read() {
        String query1 = "SELECT * FROM lamp";
        String query2 = "SELECT colour FROM lampColour";
        sqlDB = myDB.getReadableDatabase();
        Cursor cursor1 = sqlDB.rawQuery(query1, null);
        Cursor cursor2 = sqlDB.rawQuery(query2, null);
        if (cursor1.getCount() == 0 && cursor2.getCount() == 0) {
            emptyDataIcon.setVisibility(View.VISIBLE);
        }
        else {
            emptyDataIcon.setVisibility(View.GONE);
            while (cursor1.moveToNext()) {
                id.add(cursor1.getInt(0));
                ssid_name.add(cursor1.getInt(1));
                intensity.add(cursor1.getInt(2));
                status.add(cursor1.getInt(4));
            }
            while (cursor2.moveToNext()) {
                colour.add(cursor2.getString(0));
            }
            Log.d("FETCH: ", "Successful");
        }
        cursor1.close();
        cursor2.close();
    }
}
