package com.likhtman.imdbfavouritemovies.recyclerViews;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.squareup.picasso.Picasso;

public class SearchMoviesHolder extends RecyclerView.ViewHolder {
    private Activity activity;
    private TextView nameViewMovie;
    private ImageView posterViewMovie;

    public SearchMoviesHolder(Activity activity, View itemView) {
        super(itemView);
        this.activity = activity;

        // itemView is the root element of each item's layout,
        // which in our case is the LinearLayout of item_diamond.xml.
        nameViewMovie = (TextView)itemView.findViewById(R.id.NameViewMovie);
        posterViewMovie  = (ImageView)itemView.findViewById(R.id.PosterViewMovie);
    }

    // Binds the given diamond object to the layout item's UI:
    public void bindMovie(Movie oneMovie) {
        nameViewMovie.setText(oneMovie.getName());
        if (oneMovie.getUrl() == null){
            posterViewMovie.setImageResource(R.drawable.unknown);
        }else{
            Picasso.get().load(oneMovie.getUrl()).into(posterViewMovie);
        }
    }
}
