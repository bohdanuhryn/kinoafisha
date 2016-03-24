package com.bohdanuhryn.kinoafisha.client;

import com.bohdanuhryn.kinoafisha.model.data.Comment;
import com.bohdanuhryn.kinoafisha.model.responses.MoviesList;
import com.bohdanuhryn.kinoafisha.model.responses.SessionsList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @FormUrlEncoded
    @POST("/ajax/film_sessions_load")
    Call<SessionsList> getMovieSessionsList(
            @Field("film") String film,
            @Field("date") String date,
            @Field("city") String city
    );

    @FormUrlEncoded
    @POST("/ajax/comment/film/{film_name}")
    Call<ResponseBody> getMovieCommentsList(
            @Path("film_name") String filmName,
            @Field("page") long page
    );

    @FormUrlEncoded
    @POST("/ajax/comment/film/{film_name}")
    Call<ResponseBody> postComment(
            @Header("Cookie") String cookieValues,
            @Path("film_name") String filmName,
            @Field("response_to") String responseTo,
            @Field("comments") String comments
    );

    @GET("/mdauth/comeback/vkontakte")
    Call<ResponseBody> getAuth(@Query("code") String code);

}
