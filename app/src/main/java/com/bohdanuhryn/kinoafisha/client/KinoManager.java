package com.bohdanuhryn.kinoafisha.client;

import android.util.Pair;

import com.bohdanuhryn.kinoafisha.model.parameters.MovieSearchParams;
import com.bohdanuhryn.kinoafisha.model.parameters.MovieSearchQuery;
import com.bohdanuhryn.kinoafisha.model.responses.MoviesList;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class KinoManager {

    private static final String TAG = "KinoManager";

    public static final String BASE_URL = "http://kinoafisha.ua";

    private static final String QUERY_YEAR_PARAM = "year[]";
    private static final String QUERY_GENRE_PARAM = "genre[]";
    private static final String QUERY_NAME_PARAM = "name";
    private static final String QUERY_SORT_PARAM = "sort";

    private static Retrofit retrofit;

    public static boolean init() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit != null;
    }

    public static String getFullUrl(String url) {
        return BASE_URL + url;
    }

    public static Call<MoviesList> getMoviesList(MovieSearchParams params) {
        Call<MoviesList> call = null;
        if (init()) {
            IKinoApi apiService = retrofit.create(IKinoApi.class);
            call = apiService.getMoviesList(
                    params.limit,
                    params.offset,
                    getSearchQueryString(params.query)
            );
        }
        return call;
    }

    private static String getSearchQueryString(MovieSearchQuery query) {
        String q = "";
        if (query.name.length() > 0) {
            q += QUERY_NAME_PARAM + "=" + query.name;
        }
        if (query.genre.length() > 0) {
            if (q.length() > 0) {
                q += "&";
            }
            q += QUERY_GENRE_PARAM + "=" + query.genre;
        }
        if (query.sort.length() > 0) {
            if (q.length() > 0) {
                q += "&";
            }
            q += QUERY_SORT_PARAM + "=" + query.sort;
        }
        if (query.year.length() > 0) {
            if (q.length() > 0) {
                q += "&";
            }
            q += QUERY_YEAR_PARAM + "=" + query.year;
        }
        return q;
    }

}
