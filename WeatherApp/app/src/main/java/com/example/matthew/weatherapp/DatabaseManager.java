package com.example.matthew.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 10/5/2017.
 */

public class DatabaseManager {

    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }

    public void insertWeatherInfo(String city, double temp, double hum) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_CITY, city);
        values.put(DBOpenHelper.COLUMN_NAME_HUM, hum);
        values.put(DBOpenHelper.COLUMN_NAME_TEMP, temp);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    public List<String> getAllRecords() {
        Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE_NAME,
                new String[]{DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_CITY,
                        DBOpenHelper.COLUMN_NAME_TEMP,
                        DBOpenHelper.COLUMN_NAME_HUM },
                null, null, null, null, null);
        cursor.moveToFirst();
        List<String> result = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            result.add("" + cursor.getInt(0) + "," +
                cursor.getString(1) + "," +
                cursor.getString(2) + "," +
                cursor.getString(3));
            cursor.moveToNext();
        }

        return result;
    }

    public void deleteAll() {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("DELETE FROM" + DBOpenHelper.TABLE_NAME);
        }
    }

    public float getHighestTemp() {
        Cursor cursor = sqLiteDatabase.
                rawQuery("SELECT MAX(" + DBOpenHelper.COLUMN_NAME_TEMP + ") FROM " + DBOpenHelper.TABLE_NAME, null);
        cursor.moveToFirst();
        return cursor.getFloat(0);
    }

    public float getAvgHum() {
        Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                    "AVG(" + DBOpenHelper.COLUMN_NAME_HUM + ")"
                }, null, null, null, null, null);

        cursor.moveToFirst();
        return cursor.getFloat(0);
    }
}
