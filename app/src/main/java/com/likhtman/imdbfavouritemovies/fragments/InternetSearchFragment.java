package com.likhtman.imdbfavouritemovies.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.comparators.MyComparator;
import com.likhtman.imdbfavouritemovies.controllers.GlobalMovieController;
import com.likhtman.imdbfavouritemovies.globalStorage.GlobalVariables;
import com.likhtman.imdbfavouritemovies.globalStorage.TemporaryStorageSharedPreference;
import com.likhtman.imdbfavouritemovies.recyclerViews.DividerItemDecoration;
import com.likhtman.imdbfavouritemovies.recyclerViews.RecyclerTouchListener;
import com.likhtman.imdbfavouritemovies.recyclerViews.SearchMoviesAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternetSearchFragment extends Fragment implements GlobalMovieController.CallbacksListMovies{


    private EditText etxt_searchFromInternet;
    private Button btn_search;
    private Button btn_cancelIntenetSearch;
    private RecyclerView recycler_view;

    private Activity parentActivity;
    private ArrayList<Movie> movies;

    private ProgressDialog progMsg;
    private SearchMoviesAdapter searchMoviesAdapter;

    private GlobalMovieController movie_controller;

    private GlobalVariables globalVariables;



    private void initVisualElements(View view){
        btn_search = (Button)view.findViewById(R.id.btnSearch);
        btn_cancelIntenetSearch = (Button)view.findViewById(R.id.cancelIntenetSearch);
        etxt_searchFromInternet = (EditText)view.findViewById(R.id.searchFromInternet);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
    }

    private void initClickable(){
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_search = etxt_searchFromInternet.getText().toString();
                try {
                    movie_controller.getMoviesByWord(str_search);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btn_cancelIntenetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Callbacks) parentActivity).changeFragment();
            }
        });
    }

    private void initRecicleView(){
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.addItemDecoration(new DividerItemDecoration(parentActivity, LinearLayoutManager.VERTICAL));
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recycler_view, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    Movie movie = movies.get(position); // find id of clicked movie
                    ((Callbacks) parentActivity).onItemSelected(movie.getImdbID());
                } catch (Exception e) {
                    Toast.makeText(parentActivity, "There are no Movies", Toast.LENGTH_SHORT).show();
                    Log.e("GetDataFromTheInternet", e.getMessage());
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public InternetSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = getActivity();

        globalVariables = GlobalVariables.getInstance();
        movie_controller = new GlobalMovieController(this, parentActivity);
        movies = new ArrayList<Movie>();
    }

    @Override
    public void onResume() {
        super.onResume();
        //globalVariables.setIs_local_fragment(false);
        setListToRecicle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internet_search, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVisualElements(view);
        initClickable();
        initRecicleView();
    }


    private void setListToRecicle(){
        if (movies.size()>0)
        {
            Collections.sort(movies, new MyComparator());
            searchMoviesAdapter = new SearchMoviesAdapter(parentActivity,movies);
            recycler_view.setAdapter(searchMoviesAdapter);
        }
    }

    @Override
    public void returnMovies(ArrayList<Movie> tmpMovies) {
        movies = tmpMovies;
        setListToRecicle();
        if (progMsg.isShowing())
            progMsg.dismiss();
    }

    @Override
    public void returnDeleteAll(int deleted) {
        if (progMsg.isShowing())
            progMsg.dismiss();
    }



    @Override
    public void movieControllerStarted() {
        progMsg = new ProgressDialog(parentActivity);
        progMsg.setMessage("Loading Your data,please wait.");
        progMsg.show();
    }

    @Override
    public void movieControllerError(int httpStatusCode, String errorMessage) {
        if (progMsg.isShowing())
            progMsg.dismiss();
    }


    public interface Callbacks
    {
        void onItemSelected(String id);
        void changeFragment();
    }


}
