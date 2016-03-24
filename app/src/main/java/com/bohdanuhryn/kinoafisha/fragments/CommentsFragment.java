package com.bohdanuhryn.kinoafisha.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.bohdanuhryn.kinoafisha.LoginActivity;
import com.bohdanuhryn.kinoafisha.MovieActivity;
import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.CommentsAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.client.parser.KinoParser;
import com.bohdanuhryn.kinoafisha.model.data.Comment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Headers;
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
    @Bind(R.id.comment_send_button)
    Button sendButton;

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
        setupSendButton();
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

    private void setupSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KinoManager.getToken() != null && KinoManager.getToken().length() > 0) {
                    postComment();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0 && data != null) {
            KinoManager.getAuth(data.getStringExtra(LoginActivity.CODE_RESULT)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    setToken(response);
                    postComment();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
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
                    } catch (IOException e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void postComment() {
        KinoManager.postComment(movie.getFilmNameFromUrl(), "0", "Нормальный фильм.").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                reloadComments();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void setToken(Response<ResponseBody> response) {
        String token = response.headers().get("Set-Cookie");
        if (token != null && token.length() > 0) {
            int codePos = token.indexOf("kohanasession");
            if (codePos > -1) {
                codePos += 14;
                token = token.substring(codePos);
                int codeEndPos = token.indexOf(";");
                if (codeEndPos > -1) {
                    token = token.substring(0, codeEndPos);
                    KinoManager.setToken(token);
                }
            }
        }
    }
}
