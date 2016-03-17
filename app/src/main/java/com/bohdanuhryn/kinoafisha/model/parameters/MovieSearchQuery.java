package com.bohdanuhryn.kinoafisha.model.parameters;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 16.03.2016.
 */
public class MovieSearchQuery implements Serializable {

    public String sort;
    public String name;
    public String genre;
    public String year;

    public MovieSearchQuery() {
        sort = "";
        name = "";
        genre = "";
        year = "";
    }

}
