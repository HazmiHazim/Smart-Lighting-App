package com.iot.smart_lighting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.iot.smart_lighting.Model.SmartLampDB;


public class Main extends AppCompatActivity {

    // Variable Declaration
    CardView feature1, feature2, feature3, feature4;
    ImageView settingMenu;
    SmartLampDB myDB;
    SQLiteDatabase sqlDB;

    // Declare ESP32 Class
    Esp32 esp32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        feature1 = findViewById(R.id.cardView1);
        feature2 = findViewById(R.id.cardView2);
        feature3 = findViewById(R.id.cardView3);
        feature4 = findViewById(R.id.cardView4);
        settingMenu = findViewById(R.id.setting_btn1);

        myDB = new SmartLampDB(Main.this);

        // Instantiate ESP32 class
        esp32 = new Esp32(Main.this);

        // Call SQLite function to create data if not exists, if exists then do nothing
        createOrRetrieve();

        // Call ping function to connect with ESP32
        esp32.pingESP32();

        settingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the menu layout
                PopupMenu popupMenu = new PopupMenu(Main.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.setting, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.darkMode) {
                            // Handle dark mode
                        }
                        else {
                            // Handle light mode
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

        feature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, LampController.class);
                startActivity(intent);
            }
        });

        feature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Timer.class);
                startActivity(intent);
            }
        });

        feature3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, ColourEditor.class);
                startActivity(intent);
            }
        });

        feature4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, DataAnalysis.class);
                startActivity(intent);
            }
        });
    }

    public  void createOrRetrieve() {
        // Use try-finally to ensure db is close no matter what happen
        try {
            // Open The Database for Reading
            sqlDB = myDB.getReadableDatabase();
            String query = "SELECT * FROM lamp";
            Cursor cursor = sqlDB.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                Toast.makeText(Main.this, "Locally Data is Received.", Toast.LENGTH_SHORT).show();
            }
            else {
                String esp32Ssid = esp32.getESP32Ssid();
                Log.d("SSID", "Retrieved SSID: " + esp32Ssid);
                Log.d("SSID", "Comparison result: " + esp32Ssid.equalsIgnoreCase("\"ESP32-SMART-LIGHTING\""));
                if (esp32Ssid.equalsIgnoreCase("\"ESP32-SMART-LIGHTING\"")) {
                    // Lamp 1
                    createData(1, esp32Ssid, 0, 1, 0);
                    // Lamp 2
                    createData(2, esp32Ssid, 0, 1, 0);
                    // Lamp 3
                    createData(3, esp32Ssid, 0, 1, 0);
                }
                else {
                    Toast.makeText(Main.this, "Connection Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        finally {
            //sqlDB.close();
        }
    }

    // Function to create a data into local database by given parameters
    private void createData(int id, String ssid, int intensity, int connection, int status) {
        sqlDB = myDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("ssid_name", ssid);
        cv.put("intensity", intensity);
        cv.put("connection", connection);
        cv.put("status", status);
        sqlDB.insert("lamp", null, cv);
    }
}
