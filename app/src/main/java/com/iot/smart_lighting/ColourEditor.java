package com.iot.smart_lighting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.smart_lighting.Model.SmartLampDB;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;

public class ColourEditor extends AppCompatActivity {

    // Variable Declaration
    ImageView back, setting;
    ColorPicker colourWheel;
    SaturationBar saturation;
    OpacityBar opacity;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colour_editor);

        back = findViewById(R.id.back_btn3);
        setting = findViewById(R.id.setting_btn4);
        colourWheel = findViewById(R.id.colourWheelPicker);
        saturation = findViewById(R.id.saturationBar);
        opacity = findViewById(R.id.opacityBar);

        // Create instance for SmartLamp DB
        myDB = new SmartLampDB(ColourEditor.this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Make bar function colour changing with colour wheel
        colourWheel.addSaturationBar(saturation);
        colourWheel.addOpacityBar(opacity);

        colourWheel.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);

                int rgb = Color.rgb(red, green, blue);
                System.out.println("Color Value: " + rgb);
            }
        });
    }

    // Create lamp data in SQLite
    private void create() {
        sqlDB = myDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("colour", "#FFFFFF");
        cv.put("saturation", "75");
        cv.put("opacity", "100");
        cv.put("lamp_id", "1");
        long result = sqlDB.insert("lampColour", null, cv);
        if (result == -1) {
            Log.d("CREATE: ", "Fail");
        }
        else {
            Log.d("CREATE: ", "Success");
        }
    }
}
