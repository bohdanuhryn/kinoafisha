package com.bohdanuhryn.kinoafisha.model;

import java.io.Serializable;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class Movie implements Serializable {

    public Movie() {

    }

    public Movie(String name, String smallPosterUrl, float rating) {
        this.name = name;
        this.smallPosterUrl = smallPosterUrl;
        this.rating = rating;
    }

    public String url;
    public String name;
    public String smallPosterUrl;
    public String bigPosterUrl;
    public float rating;

}
