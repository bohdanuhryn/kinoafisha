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
import com.bohdanuhryn.kinoafisha.model.Comment;
import com.bohdanuhryn.kinoafisha.model.responses.CommentsList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class CommentsFragment extends Fragment {

    public static final String TAG = "CommentsFragment";

    private View rootView;
    @Bind(R.id.comments_recycler)
    RecyclerView commentsRecycler;
    private LinearLayoutManager commentsLayoutManager;
    private CommentsAdapter commentsAdapter;

    private ArrayList<Comment> commentsArray;

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
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
            //todo
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
        KinoManager.getMovieCommentsList(""/*TODO*/, 0).enqueue(new Callback<CommentsList>() {
            @Override
            public void onResponse(Call<CommentsList> call, Response<CommentsList> response) {
                CommentsList result = response.body();
                if (result.succes) {
                    commentsArray = result.result;
                    setupCommentsAdapter();
                }
            }

            @Override
            public void onFailure(Call<CommentsList> call, Throwable t) {

            }
        });
    }
}
