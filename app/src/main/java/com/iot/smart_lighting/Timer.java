package com.iot.smart_lighting;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class Timer extends AppCompatActivity {

    // Variable Declaration
    ImageView back, setting;
    LinearLayout timerLamp1, timerLamp2, timerLamp3;
    FloatingActionButton addTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        back = findViewById(R.id.back_btn2);
        setting = findViewById(R.id.setting_btn3);
        timerLamp1 = findViewById(R.id.lNav1);
        timerLamp2 = findViewById(R.id.lNav2);
        timerLamp3 = findViewById(R.id.lNav3);
        addTimer = findViewById(R.id.addTimerBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Timer.this, Main.class);
                startActivity(intent);
            }
        });

        addTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get current time
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinutes = calendar.get(Calendar.MINUTE);

                // Create time picker (spinner) in dialog box
                TimePickerDialog timePickerSpinner = new TimePickerDialog(Timer.this, R.style.CustomTimePickerDialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                                String timeChoose = hours + " : " + minutes;
                                Toast.makeText(Timer.this, "Set Time: " + timeChoose, Toast.LENGTH_SHORT).show();
                                // Save to sql database
                            }
                        }, currentHour, currentMinutes, true);

                timePickerSpinner.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Save to sql database
                        saveTimer();
                    }
                });

                timePickerSpinner.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogTimer, int i) {
                        dialogTimer.dismiss();
                    }
                });

                timePickerSpinner.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerSpinner.setTitle("Set Timer");
                timePickerSpinner.setCancelable(true);
                timePickerSpinner.show();
            }
        });
    }

    private static void saveTimer() {
        // Save to SQL Database
    }
}
