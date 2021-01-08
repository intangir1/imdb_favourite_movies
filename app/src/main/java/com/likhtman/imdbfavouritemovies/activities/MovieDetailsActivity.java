package com.likhtman.imdbfavouritemovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends CustomMenuActivity {


    private MovieDetailsFragment movieDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle extras = getIntent().getExtras();

        setMovieDetailsFragment(extras);

    }


    private void setMovieDetailsFragment(Bundle bundle)
    {
        movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.DetailsLayoutContainer, movieDetailsFragment, "movieDetailsFragment").
                commit();
    }
}
