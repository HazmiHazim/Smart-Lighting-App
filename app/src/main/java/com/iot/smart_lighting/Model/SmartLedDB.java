package com.iot.smart_lighting.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmartLedDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmartLED.db";

    public SmartLedDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TextVoice (id INTEGER PRIMARY KEY AUTOINCREMENT);");
        db.execSQL("CREATE TABLE Lamp (id INTEGER PRIMARY KEY AUTOINCREMENT);");
        db.execSQL("CREATE TABLE LampTimer (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lamp_id INTEGER, FOREIGN KEY(lamp_id) REFERENCES Lamp(id));");
        db.execSQL("CREATE TABLE LampColour (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lamp_id INTEGER, FOREIGN KEY(lamp_id) REFERENCES Lamp(id));");
        // Other table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TextVoice");
        db.execSQL("DROP TABLE IF EXISTS Lamp");
        db.execSQL("DROP TABLE IF EXISTS LampTimer");
        db.execSQL("DROP TABLE IF EXISTS LampColour");
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
