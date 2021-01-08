package com.likhtman.imdbfavouritemovies.controllers;

import android.app.Activity;
import android.app.Application;

import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.callbacks.CallbacksWebApi;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiDeleteManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiGetManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiPostManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiPutManager;
import com.likhtman.imdbfavouritemovies.sqlitedb.DbMovieHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocalMovieController  extends Application {
    private DbMovieHandler db;

    private Activity context;

    public LocalMovieController(Activity context){
        this.context=context;
        db = new DbMovieHandler(this.context);

    }


    public ArrayList getAllMovies() throws Exception{
        try {
            ArrayList movies = db.getAllMovies();
            return movies;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public ArrayList getMoviesByWord(String word) throws Exception{
        try {
            ArrayList movies = db.getAllMoviesByWord(word);
            return movies;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public Movie getMovie(int id) throws Exception{
        try {
            Movie movie = db.getMovie(id);
            return movie;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public boolean is_imdb_exist(String imdb_id) throws Exception{
        try {
            return db.is_imdb_exist(imdb_id);
        }catch (Exception e)
        {
            throw e;
        }
    }


    public ArrayList<Movie> getMovieByName(String name) throws Exception{
        try {
            ArrayList movies = db.getMoviesByName(name);
            return movies;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public Movie addMovie(Movie movie) {
        try {
            Movie addedMovie = db.addMovie(movie);
            return addedMovie;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public Movie updateMovie(Movie movie) {
        try {
            Movie updatedMovie = db.updateMovie(movie);
            return updatedMovie;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public int deleteMovie(int id) throws NullPointerException{
        try {
            int deletedMovies = db.deleteMovie(id);
            return deletedMovies;
        }catch (Exception e)
        {
            throw e;
        }
    }


    public int deleteAll() {
        try {
            int deletedMovies = db.deleteAll();
            return deletedMovies;
        }catch (Exception e)
        {
            throw e;
        }
    }
}
