package com.bohdanuhryn.kinoafisha.client;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public interface IKinoApi {

    @GET("/films")
    Call<ResponseBody> getFilms(
            @Query("name") String name
    );

}
