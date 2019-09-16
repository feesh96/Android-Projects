package com.example.matthew.moviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 10/10/2017.
 */

public class DBManager {
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }

    public void insertMovieInfo(String posterPath, String title,
                                String popularity, String voteAvg, String overview) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_POSTER_PATH, posterPath);
        values.put(DBOpenHelper.COLUMN_NAME_TITLE, title);
        values.put(DBOpenHelper.COLUMN_NAME_POPULARITY, popularity);
        values.put(DBOpenHelper.COLUMN_NAME_VOTE_AVERAGE, voteAvg);
        values.put(DBOpenHelper.COLUMN_NAME_OVERVIEW, overview);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    public List<Movie> getAllRecords() {
        Cursor cursor = sqLiteDatabase.query(DBOpenHelper.TABLE_NAME,
                new String[]{DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_POSTER_PATH,
                        DBOpenHelper.COLUMN_NAME_TITLE,
                        DBOpenHelper.COLUMN_NAME_POPULARITY,
                        DBOpenHelper.COLUMN_NAME_VOTE_AVERAGE,
                        DBOpenHelper.COLUMN_NAME_OVERVIEW},
                null, null, null, null, null);
        cursor.moveToFirst();
        Movie movie;
        List<Movie> result =new  ArrayList<Movie>();
        while (!cursor.isAfterLast()) {
            movie = new Movie();
            movie.setTitle(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_TITLE)));
            movie.setAvgVote(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_VOTE_AVERAGE)));
            movie.setPopularity(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POPULARITY)));
            movie.setOverview(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_OVERVIEW)));
            movie.setPosterPath(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POPULARITY)));
            cursor.moveToNext();
            result.add(movie);
        }

        return result;
    }

    public List<Movie> OrderBy(String key) {
        switch (key) {
            case "COLUMN_NAME_TITLE":
                return orderByTitle(getAllRecords());
            case "COLUMN_NAME_POPULARITY":
                return orderByPopularity(getAllRecords());
            case "COLUMN_NAME_VOTE_AVERAGE":
                return orderByVote(getAllRecords());
            default:
                return orderByTitle(getAllRecords());
        }
    }

    public List<Movie> orderByTitle(List<Movie> records) {
        return null;
    }

    public List<Movie> orderByVote(List<Movie> records) {
        return null;
    }

    public List<Movie> orderByPopularity(List<Movie> records) {
        return null;
    }

    public void deleteAll() {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("DELETE FROM" + DBOpenHelper.TABLE_NAME);
        }
    }

    public List<String> getFavoriteRecords() {
        return null;
    }
}
