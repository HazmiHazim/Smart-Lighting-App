package com.iot.smart_lighting;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.smart_lighting.Model.SmartLampDB;


public class LampController extends AppCompatActivity {

    // Variable Declaration
    Switch switch1, switch2, switch3;
    ImageView back, setting, bulb1, bulb2, bulb3, bulb1_on, bulb2_on, bulb3_on;
    SeekBar intensity1, intensity2, intensity3;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;

    // Declare ESP32 Class
    Esp32 esp32;

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

        // Instantiate ESP32 Class
        esp32 = new Esp32(LampController.this);

        // Call getLampState Function To Get Lamp State
        //esp32.getLampState();

        // Get initial state of each lamp
        getInitialStates(switch1, bulb1, bulb1_on, intensity1, 1, "http://192.168.4.1/state1/on", "http://192.168.4.1/state1/off");
        getInitialStates(switch2, bulb2, bulb2_on, intensity2, 2, "http://192.168.4.1/state2/on", "http://192.168.4.1/state2/off");
        getInitialStates(switch3, bulb3, bulb3_on, intensity3, 3, "http://192.168.4.1/state3/on", "http://192.168.4.1/state3/off");

        // Event when click switch 1
        eventSwitch(switch1, bulb1, bulb1_on, intensity1, 1, "http://192.168.4.1/lamp1/on", "http://192.168.4.1/lamp1/off");
        // Event when click switch 2
        eventSwitch(switch2, bulb2, bulb2_on, intensity2, 2, "http://192.168.4.1/lamp2/on", "http://192.168.4.1/lamp2/off");
        // Event when click switch 3
        eventSwitch(switch3, bulb3, bulb3_on, intensity3, 3, "http://192.168.4.1/lamp3/on", "http://192.168.4.1/lamp3/off");

        // Event when click back icon button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Function to Set Initial State of Switch Button
    private void getInitialStates(Switch switchButton, ImageView bulbOff, ImageView bulbOn, SeekBar intensity, int lampId, String onEndPoint, String offEndPoint) {
        // Use try-finally to ensure db is close no matter what happen
        try {
            String query = "SELECT * FROM lamp WHERE id = ?";
            sqlDB = myDB.getReadableDatabase();
            Cursor cursor = sqlDB.rawQuery(query, new String[] {String.valueOf(lampId)});
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                if (status == 0) {
                    switchButton.setChecked(false);
                    bulbOff.setVisibility(View.VISIBLE);
                    bulbOn.setVisibility(View.GONE);
                    intensity.setVisibility(View.GONE);
                    esp32.getLampStates(onEndPoint);
                }
                else {
                    switchButton.setChecked(true);
                    bulbOff.setVisibility(View.GONE);
                    bulbOn.setVisibility(View.VISIBLE);
                    intensity.setVisibility(View.VISIBLE);
                    esp32.getLampStates(offEndPoint);
                }
            }
        }
        finally {
            //sqlDB.close();
        }
    }

    // Function for Switch Click Event
    private void eventSwitch(Switch switchButton, ImageView bulbOff, ImageView bulbOn, SeekBar intensity, int lampId, String onEndpoint, String offEndpoint) {
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    bulbOff.setVisibility(View.GONE);
                    bulbOn.setVisibility(View.VISIBLE);
                    intensity.setVisibility(View.VISIBLE);
                    esp32.applyLamp(onEndpoint);
                    updateLampState(lampId, 1);
                }
                else {
                    bulbOff.setVisibility(View.VISIBLE);
                    bulbOn.setVisibility(View.GONE);
                    intensity.setVisibility(View.GONE);
                    esp32.applyLamp(offEndpoint);
                    updateLampState(lampId, 0);
                }
            }
        });
    }

    // Function to update initial state of each lamp
    private void updateLampState(int lampId, int newStatus) {
        // Use try-finally to ensure db is close no matter what happen
        try {
            // Open The Database for Reading
            sqlDB = myDB.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("status", newStatus);
            long result = sqlDB.update("lamp", cv, "id = ?", new String[] {String.valueOf(lampId)});
        }
        finally {
            sqlDB.close();
        }
    }
}
