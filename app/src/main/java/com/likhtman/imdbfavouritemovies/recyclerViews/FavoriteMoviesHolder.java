package com.likhtman.imdbfavouritemovies.recyclerViews;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.squareup.picasso.Picasso;

public class FavoriteMoviesHolder extends RecyclerView.ViewHolder {
    private Activity activity;
    View itemView;

    private ImageView imageViewPlace;
    private TextView nameViewMovie;
    private TextView yearViewMovie;
    private TextView rateViewMovie;

    public FavoriteMoviesHolder(Activity activity, View itemView) {
        super(itemView);
        this.activity = activity;
        this.itemView = itemView;

        // itemView is the root element of each item's layout,
        // which in our case is the LinearLayout of item_diamond.xml.


        imageViewPlace = (ImageView)itemView.findViewById(R.id.ImageViewMovie);
        nameViewMovie = (TextView)itemView.findViewById(R.id.NameViewMovie);
        yearViewMovie = (TextView)itemView.findViewById(R.id.YearViewMovie);
        rateViewMovie = (TextView)itemView.findViewById(R.id.RateViewMovie);
    }

    // Binds the given diamond object to the layout item's UI:
    public void bindMovie(Movie oneMovie) {
        nameViewMovie.setText(oneMovie.getName());
        yearViewMovie.setText(oneMovie.getDate()+"");
        rateViewMovie.setText(oneMovie.getRate()+"");


        // get pictures by their urls
        if (oneMovie.getUrl() == null){
            imageViewPlace.setImageResource(R.drawable.unknown);
        }else{
            Picasso.get().load(oneMovie.getUrl()).into(imageViewPlace);
        }


        int g = 255/10*(int)oneMovie.getRate();
        int r = 255-255/10*(int)oneMovie.getRate();


        if (oneMovie.getRate()>5) itemView.setBackgroundColor(Color.argb(255, 0, g, 0));
        else itemView.setBackgroundColor(Color.argb(255, r, 0, 0));
    }
}
