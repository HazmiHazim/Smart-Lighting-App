package com.iot.smart_lighting;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iot.smart_lighting.Adapter.TimerAdapter;
import com.iot.smart_lighting.Model.SmartLampDB;

import java.util.ArrayList;
import java.util.Calendar;

public class Timer extends AppCompatActivity {

    // Variable Declaration
    ImageView back, setting, noTimerData;
    LinearLayout lampNavTimer, timerLamp1, timerLamp2, timerLamp3;
    LinearLayout timerLampArr[];
    FloatingActionButton addTimer;
    RecyclerView recyclerView;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;
    TimerAdapter adapter;
    View selector1, selector2, selector3;
    View selectorArr[];

    private String timeChoose;

    // Variable to store the current selected index
    int selectedIndex = 0;

    // Initialize Arraylist Globally
    ArrayList<String> time = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        back = findViewById(R.id.back_btn2);
        setting = findViewById(R.id.setting_btn3);
        noTimerData = findViewById(R.id.noTimerFound);
        lampNavTimer = findViewById(R.id.lampNavigationTimer);
        timerLamp1 = findViewById(R.id.lNav1);
        timerLamp2 = findViewById(R.id.lNav2);
        timerLamp3 = findViewById(R.id.lNav3);
        addTimer = findViewById(R.id.addTimerBtn);
        recyclerView = findViewById(R.id.recyclerViewTimer);
        selector1 = findViewById(R.id.lNavSelector1);
        selector2 = findViewById(R.id.lNavSelector2);
        selector3 = findViewById(R.id.lNavSelector3);

        // Create instance for SmartLampDB
        myDB = new SmartLampDB(Timer.this);

        // Attach TimerAdapter to this class
        adapter = new TimerAdapter(Timer.this, time);
        recyclerView.setLayoutManager(new LinearLayoutManager(Timer.this));
        recyclerView.setAdapter(adapter);

        // Add all timerLamp LinearLayouts to the array
        timerLampArr = new LinearLayout[] {timerLamp1, timerLamp2, timerLamp3};

        // Add all selectors to the array
        selectorArr = new View[] {selector1, selector2, selector3};

        for (int i = 0; i < timerLampArr.length; i++) {
            final int index = i;
            timerLampArr[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectNavigationPage(index);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Timer.this, Main.class);
                startActivity(intent);
            }
        });

        read();

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
                                timeChoose = checkDigit(hours) + " : " + checkDigit(minutes);
                                create(timeChoose);
                                noTimerData.setVisibility(View.GONE);
                                time.add(timeChoose);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(Timer.this, "Set Time: " + timeChoose, Toast.LENGTH_SHORT).show();
                                // Save to sql database
                            }
                        }, currentHour, currentMinutes, true);

                timePickerSpinner.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Save to sql database
                        //addTimer(timeChoose);
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

    // Store value 0 for timepicker
    public String checkDigit(int timeDigit) {
        return timeDigit <= 9 ? "0" + timeDigit : String.valueOf(timeDigit);
    }

    // Method add to database
    private void create(String timeChoose) {
        // Save to SQL Database
        sqlDB = myDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("time", timeChoose);
        cv.put("status", "0");
        cv.put("lamp_id", "1");
        long result = sqlDB.insert("lampTimer", null, cv);
        if (result == -1) {
            Log.d("CREATE: ", "Fail");
        }
        else {
            Log.d("CREATE: ", "Success");
        }
    }

    private void read() {
        String query = "SELECT * FROM lampTimer WHERE lamp_id = 1";
        sqlDB = myDB.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            noTimerData.setVisibility(View.VISIBLE);
        }
        else {
            noTimerData.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                time.add(cursor.getString(1));
            }
        }
        cursor.close();
    }

    // Create a method to handle the navigation page
    private void selectNavigationPage(int index) {
        if (index != selectedIndex) {
            selectorArr[selectedIndex].setVisibility(View.GONE); // Hide the current selector
            selectorArr[index].setVisibility(View.VISIBLE); // Show the selector for the selected page
            selectedIndex = index; // Update the selected index

            // Perform any other actions or updates based on the selected index
            // For example, you can update the content displayed on the screen
        }
    }
}
