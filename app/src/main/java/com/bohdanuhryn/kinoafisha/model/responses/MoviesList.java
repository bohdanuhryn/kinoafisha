package com.bohdanuhryn.kinoafisha.model.responses;

import com.bohdanuhryn.kinoafisha.model.data.Movie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 16.03.2016.
 */
public class MoviesList implements Serializable {

    public boolean succes;
    public long count;
    public ArrayList<Movie> result;

}
