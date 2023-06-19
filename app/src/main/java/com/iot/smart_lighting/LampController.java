package com.iot.smart_lighting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.smart_lighting.Model.SmartLampDB;

public class LampController extends AppCompatActivity {

    // Variable Declaration
    Switch switch1, switch2, switch3;
    ImageView back, setting, bulb1, bulb2, bulb3, bulb1_on, bulb2_on, bulb3_on;
    SeekBar intensity1, intensity2, intensity3;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;
    String networkName;
    int lampStatus;
    int intensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lamp_controller);

        back = findViewById(R.id.back_btn1);
        setting = findViewById(R.id.setting_btn2);

        // Assign XML to variable for Lamp 1
        switch1 = findViewById(R.id.lSwitch1);
        bulb1 = findViewById(R.id.lImage1);
        bulb1_on = findViewById(R.id.lImage1_on);
        intensity1 = findViewById(R.id.seekbar1);

        // Assign XML to variable for Lamp 2
        switch2 = findViewById(R.id.lSwitch2);
        bulb2 = findViewById(R.id.lImage2);
        bulb2_on = findViewById(R.id.lImage2_on);
        intensity2 = findViewById(R.id.seekbar2);

        // Assign XML to variable for Lamp 3
        switch3 = findViewById(R.id.lSwitch3);
        bulb3 = findViewById(R.id.lImage3);
        bulb3_on = findViewById(R.id.lImage3_on);
        intensity3 = findViewById(R.id.seekbar3);

        // Create instance for SmartLampDB
        myDB = new SmartLampDB(LampController.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LampController.this, Main.class);
                startActivity(intent);
            }
        });

        // Event when click switch 1
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton switchButton, boolean a) {
                if(switchButton.isChecked())
                {
                    bulb1.setVisibility(View.GONE);
                    bulb1_on.setVisibility(View.VISIBLE);
                    intensity1.setVisibility(View.VISIBLE);
                    // LED connection....
                    //
                    // User must connect with ESP32 to create the data otherwise it cannot be created
                    // THIS IS JUST A DUMMY ------EDIT THIS PART ONWARDS---------
                    networkName = "ESP32 Network";
                    lampStatus = 1;
                    intensity = 20;
                    sqlDB = myDB.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT id FROM lamp WHERE id = 1", null);
                    if (cursor.moveToFirst()) {
                        update(1, intensity, lampStatus);
                    }
                    else {
                        create(networkName, lampStatus, intensity);
                    }
                }
                else
                {
                    // THIS IS JUST A DUMMY ------EDIT THIS PART ONWARDS---------
                    lampStatus = 0;
                    intensity = 20;
                    update(1, intensity, lampStatus);
                    // LED connection....
                    //
                    //
                    bulb1.setVisibility(View.VISIBLE);
                    bulb1_on.setVisibility(View.GONE);
                    intensity1.setVisibility(View.GONE);
                }
            }
        });

        // Event when click switch 2
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton switchButton2, boolean b) {
                if(switchButton2.isChecked())
                {
                    bulb2.setVisibility(View.GONE);
                    bulb2_on.setVisibility(View.VISIBLE);
                    intensity2.setVisibility(View.VISIBLE);
                    // LED connection....
                    //
                    //
                }
                else
                {
                    // LED connection....
                    //
                    //
                    bulb2.setVisibility(View.VISIBLE);
                    bulb2_on.setVisibility(View.GONE);
                    intensity2.setVisibility(View.GONE);
                }
            }
        });

        // Event when click switch 3
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton switchButton3, boolean c) {
                if(switchButton3.isChecked())
                {
                    sqlDB = myDB.getWritableDatabase();
                    sqlDB.delete("lamp", null, null);
                    sqlDB.delete("lampTimer", null, null);
                    sqlDB.delete("lampColour", null, null);
                    bulb3.setVisibility(View.GONE);
                    bulb3_on.setVisibility(View.VISIBLE);
                    intensity3.setVisibility(View.VISIBLE);
                    // LED connection....
                    //
                    //
                }
                else
                {
                    // LED connection....
                    //
                    //
                    bulb3.setVisibility(View.VISIBLE);
                    bulb3_on.setVisibility(View.GONE);
                    intensity3.setVisibility(View.GONE);
                }
            }
        });
    }

    // Create lamp data in SQLite
    private void create(String networkName, int lampStatus, int intensity) {
        sqlDB = myDB.getWritableDatabase();
        myDB.onUpgrade(sqlDB, 2, 1);
        ContentValues cv = new ContentValues();
        cv.put("ssid_name", networkName);
        cv.put("connection", "1");
        cv.put("status", lampStatus);
        cv.put("intensity", intensity);
        long id = sqlDB.insert("lamp", null, cv);
        if (id == -1) {
            Log.d("CREATE: ", "Fail");
        }
        else {
            Log.d("CREATE: ", "Success");
        }
        //sqlDB.close();
    }

    // Update lamp data
    private void update(int id, int intensity, int lampStatus){
        sqlDB = myDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", lampStatus);
        long result = sqlDB.update("lamp", cv, "id=?", new String[] {String.valueOf(id)} );
        if (result == -1) {
            Log.d("UPDATE: ", "Fail");
        }
        else {
            Log.d("UPDATE: ", "Success");
        }
        //sqlDB.close();
    }

    // Delete table lamp
    private void delete() {
        sqlDB = myDB.getWritableDatabase();
        sqlDB.delete("lamp", "id=?", new String[] {String.valueOf(1)});
        sqlDB.close();
        Log.d("DELETE: ", "Successful");
    }
}
