package com.bohdanuhryn.kinoafisha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MoviePagerAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.common.ConvertersUtils;
import com.bohdanuhryn.kinoafisha.model.data.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MovieFragment extends Fragment {

    public static final String TAG = "MovieFragment";

    public static final String MOVIE_ARGUMENT = "movie_object";

    private View rootView;

    @Bind(R.id.movie_name)
    TextView nameView;
    @Bind(R.id.movie_rating)
    TextView ratingView;
    @Bind(R.id.movie_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.movie_poster)
    ImageView posterView;
    @Bind(R.id.movie_countries)
    TextView countriesView;
    @Bind(R.id.movie_actors)
    TextView actorsView;
    @Bind(R.id.movie_rejisser)
    TextView rejisserView;
    @Bind(R.id.movie_pager)
    ViewPager moviePager;
    @Bind(R.id.movie_tabs)
    TabLayout movieTabs;

    private Movie movie;

    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_ARGUMENT, movie);
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
        readArgs();
        setupMovieViewsAtStart();
        setupMoviePager();
        return rootView;
    }

    private void readArgs() {
        if (getArguments() != null) {
            movie = (Movie)getArguments().getSerializable(MOVIE_ARGUMENT);
        }
        if (movie == null) {
            movie = new Movie();
        }
    }

    private void setupMovieViewsAtStart() {
        if (movie != null) {
            nameView.setText(movie.name);
            Picasso.with(getActivity()).load(KinoManager.getFullUrl(movie.image)).into(posterView);
            ratingView.setText(movie.vote);
            ratingBar.setRating(ConvertersUtils.getFloatFromString(movie.vote) / 10);
            countriesView.setText(movie.countries);
            actorsView.setText(Html.fromHtml(movie.actors));
            rejisserView.setText(Html.fromHtml(movie.rejisser));
            countriesView.setMovementMethod(LinkMovementMethod.getInstance());
            actorsView.setMovementMethod(LinkMovementMethod.getInstance());
            rejisserView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void setupMoviePager() {
        MoviePagerAdapter adapter = new MoviePagerAdapter(getActivity(), getActivity().getSupportFragmentManager(), movie);
        moviePager.setAdapter(adapter);
        movieTabs.setTabsFromPagerAdapter(adapter);
        moviePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(movieTabs));
    }
}
