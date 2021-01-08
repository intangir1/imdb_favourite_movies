package com.likhtman.imdbfavouritemovies.recyclerViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;

import java.util.ArrayList;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesHolder> {

    private Activity activity;
    private ArrayList<Movie> movies;

    public SearchMoviesAdapter(Activity activity, ArrayList<Movie> movies) {
        this.activity = activity;
        this.movies = movies;
    }

    // Automatically invoked when it should insert a new item to the list.
    // This method is done only for the few items that are shown in the list
    // and not for the rest of the items which will be presented on scrolling:
    public SearchMoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get a layout inflater for inflating the layout xml:
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the layout xml for only the few initial presented items:
        View linearLayout = layoutInflater.inflate(R.layout.search_item_place, parent, false); // false = don't append the inflate to the root but create a new one.

        // Return back a recycled holder object:
        return new SearchMoviesHolder(activity, linearLayout);
    }

    // Automatically invoked for each item presented in the list, when the item is shown,
    // for updating the holder object with the item's internal data:
    public void onBindViewHolder(SearchMoviesHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bindMovie(movie);

        if (position%2==0) holder.itemView.setBackgroundColor(Color.argb(255, 240, 240, 240));
        else holder.itemView.setBackgroundColor(Color.argb(255, 255, 255, 255));
    }

    // Return the items count:
    public int getItemCount() {
        return movies.size();
    }
}