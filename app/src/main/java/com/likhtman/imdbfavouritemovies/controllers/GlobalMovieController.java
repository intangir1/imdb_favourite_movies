package com.likhtman.imdbfavouritemovies.controllers;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.callbacks.CallbacksWebApi;
import com.likhtman.imdbfavouritemovies.globalStorage.TemporaryStorageSharedPreference;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiDeleteManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiGetManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiPostManager;
import com.likhtman.imdbfavouritemovies.managers.apimanagers.ApiPutManager;
import com.likhtman.imdbfavouritemovies.sqlitedb.DbMovieHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GlobalMovieController extends Application implements CallbacksWebApi {
    private CallbacksForController callbacksForController;
    private CallbacksListMovies callbacksListMovies;
    private CallbacksSingleMovie callbacksSingleMovie;

    private Activity context;

    private final String ImdbId = "6be7970d";

    private Exception tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return null;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    @Override
    public void onAboutToBegin() {
        callbacksForController.movieControllerStarted();
    }

    @Override
    public void onSuccess(Object object) throws JSONException {
        if (object instanceof JSONObject){
            Movie myMovie = new Movie();
            JSONObject Jobject = (JSONObject)object;

            try {
                myMovie.setImdbID(Jobject.getString("imdbID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                myMovie.setName(Jobject.getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                myMovie.setPlot(Jobject.getString("Plot"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                myMovie.setDate(Jobject.getInt("Year"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                myMovie.setUrl(Jobject.getString("Poster"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                myMovie.setRate(Float.parseFloat(Jobject.getString("imdbRating")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            callbacksSingleMovie.returnMovie(myMovie);
        }

        else if (object instanceof JSONArray){
            try{
                ArrayList<Movie> listdata = new ArrayList<Movie>();
                JSONArray jArray = (JSONArray)object;
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        Movie myMovie = new Movie();
                        JSONObject Jobject = jArray.getJSONObject(i);
                        try {
                            myMovie.setImdbID(Jobject.getString("imdbID"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            myMovie.setName(Jobject.getString("Title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            myMovie.setUrl(Jobject.getString("Poster"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listdata.add(myMovie);
                    }
                }
                callbacksListMovies.returnMovies(listdata);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(int httpStatusCode, String errorMessage) {
        callbacksForController.movieControllerError(httpStatusCode, errorMessage);
    }



    public GlobalMovieController(CallbacksForController callbacksForController, Activity context){
        this.context=context;
        this.callbacksForController = callbacksForController;

        if (callbacksForController instanceof CallbacksListMovies)
            this.callbacksListMovies = (CallbacksListMovies)callbacksForController;
        else
            this.callbacksSingleMovie = (CallbacksSingleMovie)callbacksForController;
    }


    public void getAllMovies() throws Exception{
        try {
            ApiGetManager apiGetManager = new ApiGetManager(this);
            apiGetManager.execute();
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void getMoviesByWord(String word) throws Exception{
        try {
            ApiGetManager apiGetManager = new ApiGetManager(this);
            apiGetManager.execute("https://www.omdbapi.com/?apikey=" + ImdbId + "&s=" + word);
        }catch (Exception e)
        {
            throw e;
        }
    }
    // https://www.omdbapi.com/?apikey=6be7970d&s=oz

    public void getMovie(String id) throws Exception{
        try {
            ApiGetManager apiGetManager = new ApiGetManager(this);
            apiGetManager.execute("https://www.omdbapi.com/?apikey=" + ImdbId + "&i=" + id);
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void getMovieByName(String name) throws Exception{
        try {
            ApiGetManager apiGetManager = new ApiGetManager(this);
            apiGetManager.execute("https://www.omdbapi.com/?apikey=" + ImdbId + "&t=" + name);
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void addMovie(Movie movie) {
        try {
            ApiPostManager apiPostManager = new ApiPostManager(this);
            apiPostManager.execute();
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void updateMovie(Movie movie) {
        try {
            ApiPutManager apiPutManager = new ApiPutManager(this);
            apiPutManager.execute();
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void deleteMovie(int id) throws NullPointerException{
        try {
            ApiDeleteManager apiDeleteManager = new ApiDeleteManager(this);
            apiDeleteManager.execute();
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void deleteAll() {
        try {
            ApiDeleteManager apiDeleteManager = new ApiDeleteManager(this);
            apiDeleteManager.execute();
        }catch (Exception e)
        {
            throw e;
        }
    }


    public interface CallbacksForController {
        void movieControllerStarted();
        void movieControllerError(int httpStatusCode, String errorMessage);
    }

    public interface CallbacksListMovies extends CallbacksForController{
        void returnMovies(ArrayList<Movie> movies);
        void returnDeleteAll(int deleted);
    }

    public interface CallbacksSingleMovie  extends CallbacksForController{
        void returnMovie(Movie movie);
        void returnDelete(int deleted);
    }


}
