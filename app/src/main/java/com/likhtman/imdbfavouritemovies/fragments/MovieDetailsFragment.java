package com.likhtman.imdbfavouritemovies.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.likhtman.imdbfavouritemovies.ExternalServices.Camera;
import com.likhtman.imdbfavouritemovies.R;
import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.controllers.GlobalMovieController;
import com.likhtman.imdbfavouritemovies.controllers.LocalMovieController;
import com.likhtman.imdbfavouritemovies.globalStorage.TemporaryStorageSharedPreference;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class MovieDetailsFragment extends Fragment implements GlobalMovieController.CallbacksSingleMovie{

    private TemporaryStorageSharedPreference preference;

    private Movie movie;
    private Activity parentActivity;
    private GlobalMovieController global_movie_controller;
    private LocalMovieController local_movie_controller;
    private boolean is_existing;

    String imdbID= "";
    int id = -1;

    private EditText etxt_nameText;
    private EditText etxt_plotText;
    private EditText etxt_dateText;
    private EditText etxt_urlText;
    private Button btn_addOneMovie;
    private Button btn_addPhoto;
    private Button btn_showUrl;
    private CheckBox chb_checkSeen;
    private RatingBar rb_ratingBar;
    private ImageView iv_image;
    private ImageView iv_myPicture;
    private TextView txt_ratingValue;
    private ProgressDialog progMsg;

    // check if name exists
    public void checkNameDate()
    {
        if (etxt_nameText.getText().length()>0 && etxt_dateText.getText().length()>0)
            btn_addOneMovie.setEnabled(true);
        else
            btn_addOneMovie.setEnabled(false);
    }

    // check if picture exists
    public void checkPicture()
    {
        if (etxt_urlText.getText().length()>0)
            btn_showUrl.setEnabled(true);
        else
            btn_showUrl.setEnabled(false);
    }

    private void initVisualElements(View view){
        etxt_nameText = (EditText)view.findViewById(R.id.nameText);
        etxt_plotText = (EditText)view.findViewById(R.id.plotText);
        etxt_dateText = (EditText)view.findViewById(R.id.dateText);
        etxt_urlText = (EditText)view.findViewById(R.id.urlText);
        btn_addOneMovie = (Button)view.findViewById(R.id.addOneMovieBtn);
        if (!is_existing)
            btn_addOneMovie.setText(R.string.add_movie);
        else
            btn_addOneMovie.setText(R.string.update_movie);



        btn_addPhoto = (Button)view.findViewById(R.id.btnAddPhoto);
        btn_showUrl = (Button)view.findViewById(R.id.showUrlbutton);
        chb_checkSeen = (CheckBox)view.findViewById(R.id.checkSeen);
        rb_ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        iv_image = (ImageView)view.findViewById(R.id.image);
        iv_myPicture = (ImageView)view.findViewById(R.id.myPicture);
        txt_ratingValue = (TextView)view.findViewById(R.id.txtRatingValue);
    }

    // get data from raitingbar and set it to raing text (show it as number)
    public void addListenOnRatingBar() {
        rb_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txt_ratingValue.setText(String.valueOf(rating));
            }
        });
    }

    private void initClickable(){
        btn_addOneMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxt_nameText.getText().toString();
                String plot = etxt_plotText.getText().toString();


                int date = Integer.parseInt(etxt_dateText.getText().toString());


                String url = etxt_urlText.getText().toString();
                float rate = Float.parseFloat(txt_ratingValue.getText().toString());
                int mySeen;
                if (chb_checkSeen.isChecked()==true)
                    mySeen=1;
                else
                    mySeen=0;

                Movie movie_add;

                if (movie.getImdbID()!=null && !movie.getImdbID().equals(""))
                    movie_add = new Movie(name, plot, url, rate, date, mySeen, movie.getImdbID());
                else
                    movie_add = new Movie(name, plot, url, rate, date, mySeen);

                if (!is_existing)
                    local_movie_controller.addMovie(movie_add);
                else
                    local_movie_controller.updateMovie(movie_add);

                if (progMsg.isShowing())
                    progMsg.dismiss();

                preference = new TemporaryStorageSharedPreference();
                preference.writeSharedPreferenceString(parentActivity,"Favorite","MyPref","fragment");

                parentActivity.onBackPressed();

            }
        });

        btn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.getInstance().takePicture(parentActivity);
            }
        });

        btn_showUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load(movie.getUrl()).into(iv_image);
            }
        });
    }

    private void initTextChange(){
        etxt_nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                try {
                    checkNameDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        etxt_dateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                try {
                    checkNameDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        etxt_urlText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s)
            {
                try {
                    checkPicture();
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


    public MovieDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = getActivity();

        global_movie_controller = new GlobalMovieController(this, parentActivity);
        local_movie_controller = new LocalMovieController(parentActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Bundle bundle = getArguments();
        boolean ifInt = bundle.getBoolean("ifInt");

        if (ifInt)
            id = bundle.getInt("id");
        else
            imdbID = bundle.getString("id");

        initVisualElements(view);
        addListenOnRatingBar();
        initClickable();
        initTextChange();

        try {
            if (ifInt)
                local_movie_controller.getMovie(id);
            else
                global_movie_controller.getMovie(imdbID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Camera.REQUEST_TAKE_PHOTO && resultCode==RESULT_OK) {
            movie.setUrl(Camera.getInstance().getmCurrentPhotoPath());
            etxt_urlText.setText(movie.getUrl());
            Log.d("Demo Pic", "Picture is saved");
        }
    }

    @Override
    public void returnMovie(Movie movie) {
        this.movie = movie;
        etxt_nameText.setText(movie.getName());
        etxt_plotText.setText(movie.getPlot());
        etxt_dateText.setText(movie.getDate()+"");
        etxt_urlText.setText(movie.getUrl());
        rb_ratingBar.setRating(movie.getRate());


        try {
            if(movie.getId()>0)
                is_existing = true;
            else if (local_movie_controller.is_imdb_exist(movie.getImdbID()))
                is_existing = true;
            else
                is_existing = false;
        } catch (Exception e) {
            e.printStackTrace();
            is_existing = false;
        }

        try {
            checkPicture();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (progMsg.isShowing())
            progMsg.dismiss();
    }


    @Override
    public void returnDelete(int deleted) {
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
}
