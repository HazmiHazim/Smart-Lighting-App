package com.iot.smart_lighting.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmartLampDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmartLamp.db";

    public SmartLampDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table network to remember the connection between ESP32 and application
        db.execSQL("CREATE TABLE IF NOT EXISTS network (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "ssid_name VARCHAR(50) NOT NULL, ssid_key INTEGER NOT NULL)");

        // Create table lamp
        db.execSQL("CREATE TABLE IF NOT EXISTS lamp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "connection INTEGER NOT NULL, status INTEGER NOT NULL, network_id INTEGER NOT NULL," +
                " FOREIGN KEY (network_id) REFERENCES network(id));");

        // Create table timer
        db.execSQL("CREATE TABLE IF NOT EXISTS lamp_timer (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "time INTEGER NOT NULL, status INTEGER NOT NULL, lamp_id INTEGER NOT NULL, FOREIGN KEY(lamp_id) REFERENCES lamp(id));");

        // Create table colour
        db.execSQL("CREATE TABLE IF NOT EXISTS lamp_colour (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "colour VARCHAR(20) NOT NULL, saturation INTEGER, opacity INTEGER," +
                "lamp_id INTEGER NOT NULL, FOREIGN KEY (lamp_id) REFERENCES lamp(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS network");
        db.execSQL("DROP TABLE IF EXISTS lamp");
        db.execSQL("DROP TABLE IF EXISTS lamp_timer");
        db.execSQL("DROP TABLE IF EXISTS lamp_colour");
        onCreate(db);
    }

    // To downgrade your version database, uncomment this code
    /*
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    */
}
