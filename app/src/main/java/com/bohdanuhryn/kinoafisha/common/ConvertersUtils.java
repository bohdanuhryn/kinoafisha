package com.bohdanuhryn.kinoafisha.common;

/**
 * Created by BohdanUhryn on 16.03.2016.
 */
public class ConvertersUtils {

    public static float getFloatFromString(String str) {
        str = str.replace(',', '.');
        float f = 0;
        try {
            f = Float.valueOf(str);
        }
        catch (NumberFormatException e) {}
        return f;
    }

}
