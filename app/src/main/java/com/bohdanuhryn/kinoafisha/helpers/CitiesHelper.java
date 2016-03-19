package com.bohdanuhryn.kinoafisha.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.model.data.City;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 19.03.2016.
 */
public class CitiesHelper {

    private static final String TAG = "CitiesHelper";

    private static final String CITY_TAG = "city";
    private static final String VALUE_ATTR = "value";
    private static final String LAT_ATTR = "lat";
    private static final String LON_ATTR = "lon";

    private static ArrayList<City> cities;

    public static ArrayList<City> getCities(Context context) {
        if (cities == null && context != null) {
            try {
                XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                InputStream is = context.getResources().openRawResource(R.raw.cities_positions);
                InputStreamReader isr = new InputStreamReader(is);
                parser.setInput(isr);
                cities = readCities(parser);
            }
            catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return cities;
    }

    public static int getNearestCity(Context context) {
        getCities(context);
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastKnownLocation == null) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        City nearestCity = null;
        int position = 0;
        double minDist = Double.MAX_VALUE;
        double dist;
        if (cities != null && lastKnownLocation != null) {
            for (int i = 0; i < cities.size(); ++i) {
                dist = Math.sqrt(
                        Math.pow(lastKnownLocation.getLatitude() - cities.get(i).lat, 2.0)
                                + Math.pow(lastKnownLocation.getLongitude() - cities.get(i).lon, 2.0)
                );
                if (dist < minDist) {
                    nearestCity = cities.get(i);
                    minDist = dist;
                    position = i;
                }
            }
        }
        return position;
    }

    private static ArrayList<City> readCities(XmlPullParser parser) {
        ArrayList<City> array = new ArrayList<City>();
        try {
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG && parser.getName().equals(CITY_TAG)) {
                    array.add(readCity(parser));
                }
                event = parser.next();
            }
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return array;
    }

    private static City readCity(XmlPullParser parser) {
        City city = new City();
        try {
            city.id = Integer.parseInt(parser.getAttributeValue(null, VALUE_ATTR));
            city.lon = Double.parseDouble(parser.getAttributeValue(null, LON_ATTR));
            city.lat = Double.parseDouble(parser.getAttributeValue(null, LAT_ATTR));
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() == XmlPullParser.TEXT) {
                    city.name = parser.getText();
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return city;
    }

}
