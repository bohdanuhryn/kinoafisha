package com.bohdanuhryn.kinoafisha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MoviesAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.client.parser.KinoParser;
import com.bohdanuhryn.kinoafisha.model.Movie;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    private View rootView;
    @Bind(R.id.movies_recycler)
    RecyclerView moviesRecycler;
    LinearLayoutManager moviesLinearLayoutManager;
    private MoviesAdapter moviesAdapter;

    private ArrayList<Movie> moviesArray;

    private OnMainFragmentListener mainFragmentListener;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setupMoviesRecyclerView();
        setupMoviesAdapter();
        loadFilms();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainFragmentListener = (OnMainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainFragmentListener = null;
    }

    private void setupMoviesRecyclerView() {
        moviesLinearLayoutManager = new LinearLayoutManager(getActivity());
        moviesRecycler.setLayoutManager(moviesLinearLayoutManager);
    }

    private void setupMoviesAdapter() {
        moviesAdapter = new MoviesAdapter(moviesArray, getActivity());
        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (mainFragmentListener != null) {
                    mainFragmentListener.startMovieActivity(moviesArray.get(position));
                }
            }
        });
        moviesRecycler.setAdapter(moviesAdapter);
    }

    private void loadFilms() {
        KinoManager.getFilms("").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    moviesArray = KinoParser.parseMovies(response.body().string());
                    setupMoviesAdapter();
                }
                catch (IOException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public interface OnMainFragmentListener {
        public void startMovieActivity(Movie movie);
    }
}
