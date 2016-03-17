package com.bohdanuhryn.kinoafisha.model.parameters;

import java.io.Serializable;

/**
 * Created by BohdanUhryn on 16.03.2016.
 */
public class MovieSearchParams implements Serializable {

    public int limit = 10;
    public int offset = 0;
    public MovieSearchQuery query = new MovieSearchQuery();

    public MovieSearchParams() {
        limit = 10;
        offset = 0;
        query = new MovieSearchQuery();
    }

}
