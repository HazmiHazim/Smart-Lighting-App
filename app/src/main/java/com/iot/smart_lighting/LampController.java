package com.iot.smart_lighting;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iot.smart_lighting.Model.Lamp;
import com.iot.smart_lighting.Model.SmartLampDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class LampController extends AppCompatActivity {

    // Variable Declaration
    Switch switch1, switch2, switch3;
    ImageView back, setting, bulb1, bulb2, bulb3, bulb1_on, bulb2_on, bulb3_on;
    SeekBar intensity1, intensity2, intensity3;

    // Instantiate ESP32 Class
    Esp32 esp32 = new Esp32(LampController.this);

    private DatabaseReference dbRef;

    // Create array object to store lamp key
    ArrayList<String> lampKeys = new ArrayList<String>();

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
        //myDB = new SmartLampDB(LampController.this);

        // Create instance for Firebase Database Reference
        dbRef = FirebaseDatabase.getInstance().getReference();

        // Check if lamp reference is empty then create lamp reference, if not then save the ref as an array
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild("lamp")) {
                    // Create a new Lamp object
                    Lamp lamp1 = new Lamp(1, "ESP32_Hazim", 0, 0, 0);
                    Lamp lamp2 = new Lamp(2, "ESP32_Hazim", 0, 0, 0);
                    Lamp lamp3 = new Lamp(3, "ESP32_Hazim", 0, 0, 0);

                    // Push each Lamp object with a unique key to the "lamp" node
                    String key1 = dbRef.child("lamp").push().getKey();
                    String key2 = dbRef.child("lamp").push().getKey();
                    String key3 = dbRef.child("lamp").push().getKey();

                    // Push new create object to firebase
                    dbRef.child("lamp").child(key1).setValue(lamp1);
                    dbRef.child("lamp").child(key2).setValue(lamp2);
                    dbRef.child("lamp").child(key3).setValue(lamp3);
                }
                else {
                    lampKeys.clear();
                    for (DataSnapshot childSnapshot : snapshot.child("lamp").getChildren()) {
                        String childKey = childSnapshot.getKey();
                        lampKeys.add(childKey);
                    }
                    System.out.println("Lamp Keys: " + lampKeys);
                    // Call function to get initial state of lamp
                    getInitialStates(switch1, 0);
                    getInitialStates(switch2, 1);
                    getInitialStates(switch3, 2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error: ", error.toString());
            }
        });

        // Call function to ping to the ESP32
        //esp32.pingESP32();

        // Event when click switch 1
        eventSwitch(switch1, bulb1, bulb1_on, intensity1, 0);

        // Event when click switch 2
        eventSwitch(switch2, bulb2, bulb2_on, intensity2, 1);

        // Event when click switch 3
        eventSwitch(switch3, bulb3, bulb3_on, intensity3, 2);

        // Event when click back icon button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LampController.this, Main.class);
                startActivity(intent);
            }
        });
    }

    // Function to set initial state of switch button
    private void getInitialStates(Switch switchButton, int lampId) {
        dbRef.child("lamp").child(lampKeys.get(lampId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("status").getValue(Integer.class) == 0) {
                    switchButton.setChecked(false);
                }
                else {
                    switchButton.setChecked(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error: ", error.toString());
            }
        });
    }

    private void eventSwitch(Switch switchButton, ImageView bulbOff, ImageView bulbOn, SeekBar intensity, int lampId) {
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    bulbOff.setVisibility(View.GONE);
                    bulbOn.setVisibility(View.VISIBLE);
                    intensity.setVisibility(View.VISIBLE);
                    updateLampState(lampId, 1);
                    Toast.makeText(LampController.this, "Lamp " + (lampId + 1) +  " is on", Toast.LENGTH_SHORT).show();
                }
                else {
                    bulbOff.setVisibility(View.VISIBLE);
                    bulbOn.setVisibility(View.GONE);
                    intensity.setVisibility(View.GONE);
                    updateLampState(lampId, 0);
                    Toast.makeText(LampController.this, "Lamp " + (lampId + 1) +  " is off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to update initial state of each lamp
    private void updateLampState(int lampId, int newStatus) {
        Map<String, Object> update = new HashMap<>();
        update.put("status", newStatus);
        dbRef.child("lamp").child(lampKeys.get(lampId)).updateChildren(update);
    }
}
