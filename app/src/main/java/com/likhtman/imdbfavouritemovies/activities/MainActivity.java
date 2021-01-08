package com.likhtman.imdbfavouritemovies.activities;


import android.content.Intent;
import android.os.Bundle;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.fragments.FavoriteMoviesFragment;
import com.likhtman.imdbfavouritemovies.fragments.InternetSearchFragment;
import com.likhtman.imdbfavouritemovies.globalStorage.TemporaryStorageSharedPreference;

public class MainActivity extends CustomMenuActivity implements FavoriteMoviesFragment.Callbacks, InternetSearchFragment.Callbacks {


    private FavoriteMoviesFragment favoriteMoviesFragment;
    private InternetSearchFragment internetSearchFragment;

    private TemporaryStorageSharedPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFavoriteMoviesFragment();
        //setInternetSearchFragment();


    }


    @Override
    protected void onResume() {
        super.onResume();

        preference = new TemporaryStorageSharedPreference();
        String checkFragment = preference.readSharedPreferenceString(this,"MyPref","fragment");
        if (checkFragment.equals("Favorite"))
            setFavoriteMoviesFragment();
    }

    private void setFavoriteMoviesFragment()
    {
        favoriteMoviesFragment = new FavoriteMoviesFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.FrameLayoutContainer, favoriteMoviesFragment, "favoriteMoviesFragment").
                commit();
    }


    private void setInternetSearchFragment()
    {
        internetSearchFragment = new InternetSearchFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.FrameLayoutContainer, internetSearchFragment, "internetSearchFragment").
                commit();
    }

    @Override
    public void GotoAddNewMovie() {
        Intent i = new Intent(this, MovieDetailsActivity.class);
        startActivity(i);
    }

    @Override
    public void changeFragment() {
        if (getSupportFragmentManager().findFragmentByTag("favoriteMoviesFragment") == null)
            setFavoriteMoviesFragment();
        else
            setInternetSearchFragment();
    }

    @Override
    public void onItemSelected(int id) {
        try {
            Intent i = new Intent(this, MovieDetailsActivity.class);
            i.putExtra("id", id);
            i.putExtra("ifInt", true);
            startActivity(i);
        } catch (Exception e){
            e.getStackTrace();
        }
    }


    @Override
    public void onItemSelected(String id) {
        try {
            Intent i = new Intent(this, MovieDetailsActivity.class);
            i.putExtra("id", id);
            i.putExtra("ifInt", false);
            startActivity(i);
        } catch (Exception e){
            e.getStackTrace();
        }
    }
}
