package com.bohdanuhryn.kinoafisha.model.data;

import java.io.Serializable;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class Movie implements Serializable {

    public Movie() {

    }

    public Movie(String name, String image, String vote) {
        this.name = name;
        this.image = image;
        this.vote = vote;
    }

    public long id;
    public String name;
    public String url;
    public String image;
    public String bigPosterUrl;
    public String vote;
    public String count_vote;
    public boolean imdb;
    public String countries;
    public String actors;
    public String rejisser;
    public int comments_count;
    public int reviews_count;
    public int trailers_count;
    public int photos_count;
    public boolean is3d;

    public String getFilmNameFromUrl() {
        String n = "";
        int p = url.lastIndexOf("/");
        n = url.substring(p + 1);
        return n;
    }

}
