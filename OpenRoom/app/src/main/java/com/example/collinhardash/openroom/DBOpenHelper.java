package com.example.collinhardash.openroom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matthew on 12/11/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooms.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "rooms";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_ROOM_NAME = "roomName";
    public static final String COLUMN_NAME_CAPACITY = "capacity";
    public static final String COLUMN_NAME_SEATS_TAKEN = "seatsTaken";
    public static final String COLUMN_NAME_DISTANCE = "distance";
    public static final String COLUMN_NAME_BUILDING_NAME = "building";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_ROOM_NAME + " TEXT," +
                    COLUMN_NAME_CAPACITY + " TEXT," +
                    COLUMN_NAME_SEATS_TAKEN + " TEXT," +
                    COLUMN_NAME_DISTANCE + " DOUBLE," +
                    COLUMN_NAME_BUILDING_NAME + " TEXT)";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
