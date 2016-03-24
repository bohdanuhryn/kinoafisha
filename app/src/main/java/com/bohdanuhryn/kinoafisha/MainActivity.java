package com.bohdanuhryn.kinoafisha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bohdanuhryn.kinoafisha.fragments.MainFragment;
import com.bohdanuhryn.kinoafisha.fragments.MoviesSearchFragment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnMainFragmentListener {

    public static final String TAG = "MainActivity";

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        getSupportActionBar().setTitle(getString(R.string.search_films));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFragment(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createFragment();
        } else {
            fragment = (MainFragment)getSupportFragmentManager().findFragmentByTag(MainFragment.TAG);
        }
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = MainFragment.newInstance();
        fm.beginTransaction().replace(R.id.fragment_layout, fragment, MainFragment.TAG).commit();
    }

    @Override
    public void startMovieActivity(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE_ARG, movie);
        startActivity(intent);
    }
}
