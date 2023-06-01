package com.iot.smart_lighting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class LampController extends AppCompatActivity {

    Switch switch1, switch2, switch3;
    ImageView bulb1, bulb1_on,  bulb2, bulb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lamp_controller);

        switch1 = findViewById(R.id.lSwitch1);
        bulb1 = findViewById(R.id.lImage1);
        bulb1_on = findViewById(R.id.lImage1_on);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
                if(switchButton.isChecked())
                {
                    bulb1.setVisibility(View.GONE);
                    bulb1_on.setVisibility(View.VISIBLE);
                }
                else
                {
                    bulb1.setVisibility(View.VISIBLE);
                    bulb1_on.setVisibility(View.GONE);
                }
            }
        });
    }
}
