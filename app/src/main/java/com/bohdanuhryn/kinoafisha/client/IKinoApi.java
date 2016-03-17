package com.bohdanuhryn.kinoafisha.client;

import android.util.Pair;

import com.bohdanuhryn.kinoafisha.model.Comment;
import com.bohdanuhryn.kinoafisha.model.responses.MoviesList;
import com.bohdanuhryn.kinoafisha.model.responses.SessionsList;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public interface IKinoApi {

    @POST("/ajax/kinoafisha_load")
    Call<MoviesList> getKinoafisha();

    @FormUrlEncoded
    @POST("/ajax/films_load2")
    Call<MoviesList> getMoviesList(
            @Field("limit") int limit,
            @Field("offset") int offset,
            @Field("query") String query
    );

    @POST("/ajax/film_sessions_load")
    Call<SessionsList> getMovieSessionsList(
            @Field("film") long film,
            @Field("date") long date,
            @Field("city") long city
    );

    @POST("/ajax/comment/film/{film_name}")
    Call<Comment> postComment(
            @Path("film_name") String filmName
    );

}
