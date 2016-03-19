package com.bohdanuhryn.kinoafisha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bohdanuhryn.kinoafisha.fragments.MoviesSearchFragment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

public class MainActivity extends AppCompatActivity
        implements MoviesSearchFragment.OnMainFragmentListener {

    public static final String TAG = "MainActivity";

    private MoviesSearchFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createFragment();
        } else {
            fragment = (MoviesSearchFragment)getSupportFragmentManager().findFragmentByTag(MoviesSearchFragment.TAG);
        }
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = MoviesSearchFragment.newInstance();
        fm.beginTransaction().replace(R.id.fragment_layout, fragment, MoviesSearchFragment.TAG).commit();
    }

    @Override
    public void startMovieActivity(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE_ARG, movie);
        startActivity(intent);
    }
}
