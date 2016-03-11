package com.bohdanuhryn.kinoafisha;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bohdanuhryn.kinoafisha.fragments.MovieFragment;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MovieActivity extends AppCompatActivity {

    public static final String TAG = "MovieActivity";

    private MovieFragment fragment;

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
            fragment = (MovieFragment)getSupportFragmentManager().findFragmentByTag(MovieFragment.TAG);
        }
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = MovieFragment.newInstance();
        fm.beginTransaction().replace(R.id.fragment_layout, fragment, MovieFragment.TAG).commit();
    }

}
