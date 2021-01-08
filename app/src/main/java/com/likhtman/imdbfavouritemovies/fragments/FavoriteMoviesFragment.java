package com.likhtman.imdbfavouritemovies.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.comparators.MyComparator;
import com.likhtman.imdbfavouritemovies.controllers.GlobalMovieController;
import com.likhtman.imdbfavouritemovies.controllers.LocalMovieController;
import com.likhtman.imdbfavouritemovies.globalStorage.GlobalVariables;
import com.likhtman.imdbfavouritemovies.globalStorage.TemporaryStorageSharedPreference;
import com.likhtman.imdbfavouritemovies.recyclerViews.DividerItemDecoration;
import com.likhtman.imdbfavouritemovies.recyclerViews.FavoriteMoviesAdapter;
import com.likhtman.imdbfavouritemovies.recyclerViews.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment{

    private ProgressDialog progMsg;
    private Activity parentActivity;
    private ArrayList<Movie> movies;
    private EditText etxt_search;

    private ImageButton addbutton;
    private Button sortByYearButton;
    private Button sortByNameButton;

    private RecyclerView recyclerView;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;

    private LocalMovieController movie_controller;

    private GlobalVariables globalVariables;


    private void initVisualElements(View view){
        addbutton = (ImageButton)view.findViewById(R.id.addbutton);
        sortByNameButton = (Button)view.findViewById(R.id.btnSortByName);
        sortByYearButton = (Button)view.findViewById(R.id.btnSortByYear);
        etxt_search = (EditText)view.findViewById(R.id.myStr);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_fav);
    }

    private void initClickable(){
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on add button open "Choose" dialog
                final Dialog dialog = new Dialog(parentActivity);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Choose");

                // it has "manual" and "internet" adding choises
                Button manual = (Button) dialog.findViewById(R.id.manual);
                Button internet = (Button) dialog.findViewById(R.id.internet);

                // if "manual" send movie id=-1 to AddMoviesActivity
                manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Callbacks) parentActivity).GotoAddNewMovie();
                        dialog.dismiss();
                    }
                });

                // if "internet" go to InternetSearchActivity
                internet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Callbacks) parentActivity).changeFragment();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        sortByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariables.setCompareByYear(false);
                setListToRecicle();
            }
        });

        sortByYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariables.setCompareByYear(true);
                setListToRecicle();
            }
        });
    }

    private void initRecicleView(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    Movie movie = movies.get(position); // find id of clicked movie
                    ((Callbacks) parentActivity).onItemSelected(movie.getId());
                } catch (Exception e) {
                    Toast.makeText(parentActivity, "There are no Movies", Toast.LENGTH_SHORT).show();
                    Log.e("GetDataFromTheInterweb", e.getMessage());
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void initTextChange(){
        etxt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                String search = etxt_search.getText().toString();
                try {
                    movies = movie_controller.getMoviesByWord(search);
                    setListToRecicle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public FavoriteMoviesFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = getActivity();

        setHasOptionsMenu(true);
        globalVariables = GlobalVariables.getInstance();
        movie_controller = new LocalMovieController(parentActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=null;

    try {
        view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }catch (Exception e){
        e.getMessage();
    }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVisualElements(view);
        initClickable();
        initRecicleView();
        initTextChange();

    }


    @Override
    public void onResume()
    {
        super.onResume();
        try {
            movies = movie_controller.getAllMovies();
            setListToRecicle();
        } catch (Exception e) {
            e.getMessage();
        }
    }




    private void setListToRecicle(){
        if (movies.size()>0)
        {
            Collections.sort(movies, new MyComparator());
            favoriteMoviesAdapter = new FavoriteMoviesAdapter(parentActivity,movies);
            recyclerView.setAdapter(favoriteMoviesAdapter);
        }
    }

    //----------------------------------------------Callbacks-----------------------------------

    public interface Callbacks
    {
        void GotoAddNewMovie();
        void changeFragment();
        void onItemSelected(int id);
    }





    // ------------------------------------ Options Menu-------------------------------

//    public void onPrepareOptionsMenu(Menu menu)
//    {
//        MenuItem deleteMovies = menu.findItem(R.id.deleteMovies);
//        deleteMovies.setVisible(true);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.deleteMovies:
//                db.deleteAll(); // delete all data from database
//                findFavoriteMovies();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
