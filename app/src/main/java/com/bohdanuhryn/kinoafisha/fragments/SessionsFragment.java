package com.bohdanuhryn.kinoafisha.fragments;

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
import com.bohdanuhryn.kinoafisha.adapters.SessionsAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.model.Cinema;
import com.bohdanuhryn.kinoafisha.model.responses.SessionsList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class SessionsFragment extends Fragment {

    public static final String TAG = "SessionsFragment";

    private static final String FILM_ARG = "film_arg";

    private View rootView;
    @Bind(R.id.sessions_recycler)
    RecyclerView sessionsRecycler;
    private LinearLayoutManager sessionsLayoutManager;
    private SessionsAdapter sessionsAdapter;

    private long film;
    private long date;
    private long city;
    private ArrayList<Cinema> sessionsArray;

    public static SessionsFragment newInstance(long film) {
        SessionsFragment fragment = new SessionsFragment();
        Bundle args = new Bundle();
        args.putLong(FILM_ARG, film);
        fragment.setArguments(args);
        return fragment;
    }

    public SessionsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sessions, container, false);
        ButterKnife.bind(this, rootView);
        readArguments();
        setupSessionsRecyclerView();
        setupSessionsAdapter();
        reloadSessions();
        return rootView;
    }

    private void readArguments() {
        Bundle args = getArguments();
        if (args != null) {
            film = args.getLong(FILM_ARG, 0);
        }
    }

    private void setupSessionsRecyclerView() {
        sessionsLayoutManager = new LinearLayoutManager(getActivity());
        sessionsRecycler.setLayoutManager(sessionsLayoutManager);
    }

    private void setupSessionsAdapter() {
        sessionsAdapter = new SessionsAdapter(sessionsArray, getActivity());
        sessionsRecycler.setAdapter(sessionsAdapter);
    }

    private void reloadSessions() {
        KinoManager.getMovieSessionsList(film, date, city).enqueue(new Callback<SessionsList>() {
            @Override
            public void onResponse(Call<SessionsList> call, Response<SessionsList> response) {
                SessionsList result = response.body();
                if (result.succes) {
                    sessionsArray = result.result;
                    setupSessionsAdapter();
                }
            }

            @Override
            public void onFailure(Call<SessionsList> call, Throwable t) {

            }
        });
    }

}
