package com.iot.smart_lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class LampController extends AppCompatActivity {

    // Variable Declaration
    Switch switch1, switch2, switch3;
    ImageView back, setting, bulb1, bulb2, bulb3, bulb1_on, bulb2_on, bulb3_on;
    SeekBar intensity1, intensity2, intensity3;

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
                    //
                }
                else
                {
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
}
