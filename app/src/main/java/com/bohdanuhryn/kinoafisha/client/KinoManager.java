package com.bohdanuhryn.kinoafisha.client;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class KinoManager {

    private static final String TAG = "KinoManager";

    public static final String BASE_URL = "http://kinoafisha.ua";

    private static Retrofit retrofit;

    public static void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public static Call<ResponseBody> getFilms(String name) {
        Call<ResponseBody> call = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            IKinoApi apiService = retrofit.create(IKinoApi.class);
            call = apiService.getFilms(
                    name
            );
        }
        return call;
    }

}
