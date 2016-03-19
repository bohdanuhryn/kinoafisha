package com.bohdanuhryn.kinoafisha.model.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class Cinema implements Serializable {

    public String name;
    public String url;
    public String coords;
    public String address;
    public ArrayList<Hall> h;

}
