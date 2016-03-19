package com.bohdanuhryn.kinoafisha.model.responses;

import com.bohdanuhryn.kinoafisha.model.data.Comment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 17.03.2016.
 */
public class CommentsList implements Serializable {

    public boolean succes;
    public ArrayList<Comment> result;

}
