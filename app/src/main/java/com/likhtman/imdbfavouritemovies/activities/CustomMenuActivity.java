package com.likhtman.imdbfavouritemovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.sqlitedb.DbMovieHandler;

import java.util.Locale;

public class CustomMenuActivity extends AppCompatActivity {
    //protected DbMovieHandler db; // DataBase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //App.setLocaleFa(CustomMenuActivity.this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_menu);
        //db = new DbMovieHandler(this); // New DataBase
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeLanguageEnglish:
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
                return true;
            case R.id.changeLanguageHebrew:
                Locale locale2 = new Locale("iw");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
                recreate();
                return true;
            case R.id.exitApplication:
                moveTaskToBack(true);
                System.exit(0);
                return true;
            case R.id.deleteMovies:
                //db.deleteAll(); // delete all data from database
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}