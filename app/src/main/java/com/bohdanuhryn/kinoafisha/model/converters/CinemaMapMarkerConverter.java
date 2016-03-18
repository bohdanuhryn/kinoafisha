package com.bohdanuhryn.kinoafisha.model.converters;

import android.util.Log;

import com.bohdanuhryn.kinoafisha.model.Cinema;
import com.bohdanuhryn.kinoafisha.model.CinemaMapMarker;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by BohdanUhryn on 18.03.2016.
 */
public class CinemaMapMarkerConverter {

    private static final String TAG = "CinemaMapMarkerConverter";

    public static CinemaMapMarker convert(Cinema cinema) {
        CinemaMapMarker mapMarker = new CinemaMapMarker();
        mapMarker.address = cinema.address;
        mapMarker.url = cinema.url;
        mapMarker.name = cinema.name;
        String[] coordsStr = cinema.coords.split(",");
        try {
            mapMarker.position = new LatLng(Double.parseDouble(coordsStr[1]), Double.parseDouble(coordsStr[0]));
        }
        catch (NumberFormatException e) {
            mapMarker.position = new LatLng(0, 0);
            mapMarker.name = "No city with such coordinates";
            Log.e(TAG, e.getMessage());
        }
        return mapMarker;
    }

}
