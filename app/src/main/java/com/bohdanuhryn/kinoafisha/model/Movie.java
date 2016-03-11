package com.bohdanuhryn.kinoafisha.model;

import java.io.Serializable;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class Movie implements Serializable {

    public Movie() {

    }

    public Movie(String name, String posterUrl, float rating) {
        this.name = name;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    public String name;
    public String posterUrl;
    public float rating;

}
