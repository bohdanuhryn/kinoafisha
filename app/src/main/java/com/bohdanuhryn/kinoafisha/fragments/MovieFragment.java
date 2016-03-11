package com.bohdanuhryn.kinoafisha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MoviePagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MovieFragment extends Fragment {

    public static final String TAG = "MovieFragment";

    private View rootView;

    @Bind(R.id.movie_name)
    TextView nameView;
    @Bind(R.id.movie_rating)
    TextView ratingView;
    @Bind(R.id.movie_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.movie_poster)
    ImageView posterView;
    @Bind(R.id.movie_pager)
    ViewPager moviePager;
    @Bind(R.id.movie_tabs)
    TabLayout movieTabs;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MovieFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, rootView);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.movie_toolbar));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupMovieViews();
        setupMoviePager();
        return rootView;
    }

    private void setupMovieViews() {

    }

    private void setupMoviePager() {
        MoviePagerAdapter adapter = new MoviePagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        moviePager.setAdapter(adapter);
        movieTabs.setTabsFromPagerAdapter(adapter);
        moviePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(movieTabs));
    }
}
