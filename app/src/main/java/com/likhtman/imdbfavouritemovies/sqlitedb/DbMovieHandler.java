package com.likhtman.imdbfavouritemovies.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.likhtman.imdbfavouritemovies.bean.Movie;

import java.util.ArrayList;

public class DbMovieHandler extends Exception{
    private DbMovieHelper dbhelper;
    private final static String LOG_TAG = "MovieHandler";

    public DbMovieHandler(Context context) {
        dbhelper = new DbMovieHelper(context, DbMovieConstants.DATABASE_NAME, null, DbMovieConstants.DATABASE_VERSION);
    }


    public Movie getLastMovie() throws NullPointerException{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Movie movie = null;
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null,null, null, null, null, null);
            cursor.moveToLast();
            if (cursor.moveToLast()) {
                movie = new Movie(
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_PLOT)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_URL)),
                        cursor.getFloat(cursor.getColumnIndex(DbMovieConstants.MOVIE_RATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_SEEN)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_IMDB_ID))
                );
            }
            return movie;
        } catch (Exception e)
        {
            throw new NullPointerException();
        } finally {
            db.close();
        }
    }


    public ArrayList getAllMovies() throws Exception{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null, null, null, null, null, null, null);

            ArrayList<Movie> initData= new ArrayList();

            while (cursor.moveToNext()) {
                initData.add(new Movie(
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_PLOT)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_URL)),
                        cursor.getFloat(cursor.getColumnIndex(DbMovieConstants.MOVIE_RATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_SEEN)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_IMDB_ID))));
            }
            cursor.close();
            return initData;
        } catch (Exception e)
        {
            throw new Exception("There is a problem with database or its empty");
        } finally {
            db.close();
        }
    }


    public ArrayList getAllMoviesByWord(String word) throws Exception{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null, null, null, null, null, null, null);

            ArrayList<Movie> initData= new ArrayList();

            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)).toLowerCase().contains(word.toLowerCase()))
                    initData.add(new Movie(
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_ID)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_PLOT)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_URL)),
                            cursor.getFloat(cursor.getColumnIndex(DbMovieConstants.MOVIE_RATE)),
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_DATE)),
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_SEEN)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_IMDB_ID))
                    ));
            }
            cursor.close();
            return initData;
        } catch (Exception e)
        {
            throw new Exception("There is a problem with database or its empty");
        } finally {
            db.close();
        }
    }


    public ArrayList getMoviesByName(String name) throws Exception{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null, null, null, null, null, null, null);

            ArrayList<Movie> initData= new ArrayList();

            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)).toLowerCase().equals(name.toLowerCase()))
                    initData.add(new Movie(
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_ID)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_PLOT)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_URL)),
                            cursor.getFloat(cursor.getColumnIndex(DbMovieConstants.MOVIE_RATE)),
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_DATE)),
                            cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_SEEN)),
                            cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_IMDB_ID)))
                    );
            }
            cursor.close();
            return initData;
        } catch (Exception e)
        {
            throw new Exception("There is a problem with database or its empty");
        } finally {
            db.close();
        }
    }


    public Movie getMovie(int id) throws NullPointerException{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Movie movie = null;
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null, DbMovieConstants.MOVIE_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            // Check if the book was found
            if (cursor.moveToFirst()) {
                movie = new Movie(
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_PLOT)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_URL)),
                        cursor.getFloat(cursor.getColumnIndex(DbMovieConstants.MOVIE_RATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DbMovieConstants.MOVIE_SEEN)),
                        cursor.getString(cursor.getColumnIndex(DbMovieConstants.MOVIE_IMDB_ID)));
            }
            return movie;
        } catch (Exception e)
        {
            throw new NullPointerException();
        } finally {
            db.close();
        }
    }


    public boolean is_imdb_exist(String imdb_id) throws NullPointerException{
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try
        {
            Cursor cursor = db.query(DbMovieConstants.TABLE_Movies, null, DbMovieConstants.MOVIE_IMDB_ID + "=?",
                    new String[] { String.valueOf(imdb_id) }, null, null, null, null);
            // Check if the book was found
            if (cursor.moveToFirst())
                return true;
            else
                return false;
        } catch (Exception e)
        {
            throw new NullPointerException();
        } finally {
            db.close();
        }
    }


    public Movie addMovie(Movie movie) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues newMovieValues = new ContentValues();
        newMovieValues.put(DbMovieConstants.MOVIE_NAME, movie.getName());
        newMovieValues.put(DbMovieConstants.MOVIE_PLOT, movie.getPlot());
        newMovieValues.put(DbMovieConstants.MOVIE_URL, movie.getUrl());
        newMovieValues.put(DbMovieConstants.MOVIE_RATE, movie.getRate());
        newMovieValues.put(DbMovieConstants.MOVIE_DATE, movie.getDate());
        newMovieValues.put(DbMovieConstants.MOVIE_SEEN, movie.isSeen());
        newMovieValues.put(DbMovieConstants.MOVIE_IMDB_ID, movie.getImdbID());
        // Inserting the new row, or throwing an exception if an error occurred
        try {
            db.insertOrThrow(DbMovieConstants.TABLE_Movies, null, newMovieValues);
            return getLastMovie();
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        } finally {
            db.close();
        }
    }


    public Movie updateMovie(Movie movie) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues newMovieValues = new ContentValues();
        try {
            newMovieValues.put(DbMovieConstants.MOVIE_NAME, movie.getName());
            newMovieValues.put(DbMovieConstants.MOVIE_PLOT, movie.getPlot());
            newMovieValues.put(DbMovieConstants.MOVIE_URL, movie.getUrl());
            newMovieValues.put(DbMovieConstants.MOVIE_RATE, movie.getRate());
            newMovieValues.put(DbMovieConstants.MOVIE_DATE, movie.getDate());
            newMovieValues.put(DbMovieConstants.MOVIE_SEEN, movie.isSeen());
            newMovieValues.put(DbMovieConstants.MOVIE_IMDB_ID, movie.getImdbID());

            db.update(DbMovieConstants.TABLE_Movies, newMovieValues, DbMovieConstants.MOVIE_ID + "=?",
                    new String[]{String.valueOf(movie.getId())});
            return getMovie(movie.getId());


//            db.update(MovieDbConstants.TABLE_NAME, newMovieValues, MovieDbConstants.MOVIE_NAME + "=? and " + MovieDbConstants.MOVIE_DATE + "=?",
//                    new String[]{String.valueOf(movie.getName()), String.valueOf(movie.getId())});

        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }


    public int deleteMovie(int id) throws NullPointerException{
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try {
            return db.delete(DbMovieConstants.TABLE_Movies, DbMovieConstants.MOVIE_ID + "=?",
                    new String[]{String.valueOf(id)});
        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }


    public int deleteAll() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            return db.delete(DbMovieConstants.TABLE_Movies, null, null);

        } catch (SQLiteException ex) {
            Log.e(LOG_TAG, ex.getMessage());
            throw ex;
        }
        finally {
            db.close();
        }
    }

}
