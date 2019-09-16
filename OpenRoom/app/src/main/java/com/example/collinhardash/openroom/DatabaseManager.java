package com.example.collinhardash.openroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 12/11/2017.
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

    public void insertRoomInfo(String roomName,String building, String capacity, String seatsTaken,double distance) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_ROOM_NAME, roomName);
        values.put(DBOpenHelper.COLUMN_NAME_CAPACITY, capacity);
        values.put(DBOpenHelper.COLUMN_NAME_SEATS_TAKEN, seatsTaken);
        values.put(DBOpenHelper.COLUMN_NAME_DISTANCE,distance);
        values.put(DBOpenHelper.COLUMN_NAME_BUILDING_NAME,building);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_NAME, null, values);

        return;
    }

    /**
     * Returns list of all rooms in the database
     * @return list of all rooms
     */
    public ArrayList<Room> getAllRecords() {
        Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_ROOM_NAME,
                        DBOpenHelper.COLUMN_NAME_BUILDING_NAME,
                        DBOpenHelper.COLUMN_NAME_CAPACITY,
                        DBOpenHelper.COLUMN_NAME_SEATS_TAKEN,
                        DBOpenHelper.COLUMN_NAME_DISTANCE},
                null, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            Room room;
            ArrayList<Room> result = new ArrayList<Room>();
            while (!cursor.isAfterLast()) {
                room = new Room(null,null, 0, 0, 0);
                room.setRoom(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_ROOM_NAME)));
                room.setBuilding(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_BUILDING_NAME)));
                room.setCapacity(Integer.parseInt(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_CAPACITY))));
                room.setSeatsTaken(Integer.parseInt(
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_SEATS_TAKEN))));
                room.setDistance(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_DISTANCE)));
                room.setName(room.getBuilding() + ' ' + room.getRoom());
                cursor.moveToNext();
                result.add(room);
            }


            return result;
        }
        return null;
    }

    /**
     * Deletes all records in the database (used for updating DB)
     */
    public void deleteAll() {
        if (sqLiteDatabase.isOpen()) {
            Log.d("DeleteAll", "deleting all");
            sqLiteDatabase.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }

    /**
     * Sets seats taken for the given room name
     * @param roomName name of the room
     * @param seatsTaken amount of seats take
     */
    public void setSeatsTaken(String roomName, String seatsTaken) {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("UPDATE " + DBOpenHelper.TABLE_NAME + " SET " +
                    DBOpenHelper.COLUMN_NAME_SEATS_TAKEN + "='" + seatsTaken +
                    "' WHERE " + DBOpenHelper.COLUMN_NAME_ROOM_NAME + "='" + roomName + "'");
        }
    }
}