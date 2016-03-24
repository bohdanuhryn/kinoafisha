package com.bohdanuhryn.kinoafisha;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by BohdanUhryn on 24.03.2016.
 */
public class KinoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //VKSdk.initialize(this);
    }
}
