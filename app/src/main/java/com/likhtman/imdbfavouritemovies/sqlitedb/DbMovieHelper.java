package com.likhtman.imdbfavouritemovies.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbMovieHelper extends SQLiteOpenHelper {

    public DbMovieHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DbMovieConstants.LOG_TAG, "Creating all the tables");
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + DbMovieConstants.TABLE_Movies +
                " (" + DbMovieConstants.MOVIE_ID + " INTEGER PRIMARY KEY," + DbMovieConstants.MOVIE_NAME + " TEXT,"
                + DbMovieConstants.MOVIE_PLOT + " TEXT," + DbMovieConstants.MOVIE_URL + " TEXT,"
                + DbMovieConstants.MOVIE_RATE + " REAL," + DbMovieConstants.MOVIE_DATE + " INTEGER,"
                + DbMovieConstants.MOVIE_SEEN + " INTEGER," + DbMovieConstants.MOVIE_IMDB_ID + " TEXT" + ")";
        try {
            db.execSQL(CREATE_MOVIE_TABLE);
        } catch (SQLiteException ex) {
            Log.e(DbMovieConstants.LOG_TAG, "Create table exception: " +
                    ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbMovieConstants.LOG_TAG, "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + DbMovieConstants.TABLE_Movies);
        onCreate(db);
    }
}

