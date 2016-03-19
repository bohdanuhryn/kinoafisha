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
import com.bohdanuhryn.kinoafisha.adapters.CommentsAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.client.parser.KinoParser;
import com.bohdanuhryn.kinoafisha.model.data.Comment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

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
public class CommentsFragment extends Fragment {

    public static final String TAG = "CommentsFragment";

    private static final String MOVIE_ARG = "movie_arg";

    private View rootView;
    @Bind(R.id.comments_recycler)
    RecyclerView commentsRecycler;
    private LinearLayoutManager commentsLayoutManager;
    private CommentsAdapter commentsAdapter;

    private Movie movie;

    private ArrayList<Comment> commentsArray;

    public static CommentsFragment newInstance(Movie movie) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_ARG, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, rootView);
        readArguments();
        setupCommentsRecyclerView();
        setupCommentsAdapter();
        reloadComments();
        return rootView;
    }

    private void readArguments() {
        Bundle args = getArguments();
        if (args != null) {
            movie = (Movie)args.getSerializable(MOVIE_ARG);
        }
    }

    private void setupCommentsRecyclerView() {
        commentsLayoutManager = new LinearLayoutManager(getActivity());
        commentsRecycler.setLayoutManager(commentsLayoutManager);
    }

    private void setupCommentsAdapter() {
        commentsAdapter = new CommentsAdapter(commentsArray, getActivity());
        commentsRecycler.setAdapter(commentsAdapter);
    }

    private void reloadComments() {
        if (movie == null) {
            return;
        }
        KinoManager.getMovieCommentsList(movie.getFilmNameFromUrl(), 1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    String htmlStr = "";
                    try {
                        htmlStr = response.body().string();
                        commentsArray = KinoParser.parseComments(htmlStr);
                        setupCommentsAdapter();
                    }
                    catch (IOException e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
