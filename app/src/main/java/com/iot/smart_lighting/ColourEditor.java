package com.iot.smart_lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;

public class ColourEditor extends AppCompatActivity {

    // Variable Declaration
    ImageView back, setting;
    ColorPicker colourWheel;
    SaturationBar saturation;
    OpacityBar opacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colour_editor);

        back = findViewById(R.id.back_btn3);
        setting = findViewById(R.id.setting_btn4);
        colourWheel = findViewById(R.id.colourWheelPicker);
        saturation = findViewById(R.id.saturationBar);
        opacity = findViewById(R.id.opacityBar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColourEditor.this, Main.class);
                startActivity(intent);
            }
        });

        // Make bar function colour changing with colour wheel
        colourWheel.addSaturationBar(saturation);
        colourWheel.addOpacityBar(opacity);
    }
}
