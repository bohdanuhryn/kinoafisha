package com.bohdanuhryn.kinoafisha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bohdanuhryn.kinoafisha.fragments.MovieFragment;
import com.bohdanuhryn.kinoafisha.fragments.SessionsFragment;
import com.bohdanuhryn.kinoafisha.model.data.Cinema;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MovieActivity extends AppCompatActivity
        implements SessionsFragment.OnSessionsFragmentListener {

    public static final String TAG = "MovieActivity";
    public static final String MOVIE_ARG = "movie_arg";

    private MovieFragment fragment;

    private Movie movie;

    @Override
    public void onMapActivityStart(Cinema cinema) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(MapsActivity.CINEMA_ARG, cinema);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        readArgs();
        initFragment(savedInstanceState);
    }

    private void readArgs() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            movie = (Movie)args.getSerializable(MOVIE_ARG);
        }
        if (movie == null) {
            movie = new Movie();
        }
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createFragment();
        } else {
            fragment = (MovieFragment)getSupportFragmentManager().findFragmentByTag(MovieFragment.TAG);
        }
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = MovieFragment.newInstance(movie);
        fm.beginTransaction().replace(R.id.fragment_layout, fragment, MovieFragment.TAG).commit();
    }

}
