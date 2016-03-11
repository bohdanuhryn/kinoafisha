package com.bohdanuhryn.kinoafisha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MoviesAdapter;
import com.bohdanuhryn.kinoafisha.model.Movie;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        moviesArray = new ArrayList<Movie>();
        moviesArray.add(new Movie("Братья из Гримсби", "", 4.9f));
        moviesArray.add(new Movie("Ночь Святого Валентина", "", 8.5f));
        moviesArray.add(new Movie("Кэрол", "", 2.34f));
        moviesArray.add(new Movie("Дивергент. Глава 3: Преданная", "", 3.5f));

        moviesAdapter = new MoviesAdapter(moviesArray, getActivity());
        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (mainFragmentListener != null) {
                    mainFragmentListener.startMovieActivity();
                }
            }
        });
        moviesRecycler.setAdapter(moviesAdapter);
    }

    public interface OnMainFragmentListener {
        public void startMovieActivity();
    }
}
