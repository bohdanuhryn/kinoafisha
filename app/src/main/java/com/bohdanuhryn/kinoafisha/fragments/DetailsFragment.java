package com.bohdanuhryn.kinoafisha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.CommentsAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.client.parser.KinoParser;
import com.bohdanuhryn.kinoafisha.common.ConvertersUtils;
import com.bohdanuhryn.kinoafisha.model.data.Comment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment";

    private static final String MOVIE_ARG = "movie_arg";

    private View rootView;
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

    private Movie movie;

    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_ARG, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        readArguments();
        setupMovieViewsAtStart();
        return rootView;
    }

    private void readArguments() {
        Bundle args = getArguments();
        if (args != null) {
            movie = (Movie)args.getSerializable(MOVIE_ARG);
        }
    }

    private void setupMovieViewsAtStart() {
        if (movie != null) {
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
}
