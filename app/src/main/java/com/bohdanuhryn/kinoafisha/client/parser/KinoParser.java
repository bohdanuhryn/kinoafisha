package com.bohdanuhryn.kinoafisha.client.parser;

import android.util.Log;

import com.bohdanuhryn.kinoafisha.model.Comment;
import com.bohdanuhryn.kinoafisha.model.Cinema;
import com.bohdanuhryn.kinoafisha.model.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class KinoParser {

    private static final String BASE_URL = "http://kinoafisha.ua";

    private static final String IMG = "img";
    private static final String H3 = "h3";

    private static final String SRC = "src";

    private static final String ITEM = "item";

    private static final String LIST_FILMS = "list-films";
    private static final String PHOTO = "photo";
    private static final String RATING = "vote";
    private static final String STAR = "star";

    public static ArrayList<Movie> parseMovies(String htmlStr) {
        ArrayList<Movie> array = new ArrayList<Movie>();
        Document doc = Jsoup.parse(htmlStr);
        if (doc.getElementsByClass(LIST_FILMS).size() > 0) {
            Elements movies = doc.getElementsByClass(LIST_FILMS).get(0).getElementsByClass(ITEM);
            Elements tempElements;
            for (Element m : movies) {
                Movie mov = new Movie();
                // parsing of movie title
                tempElements = m.getElementsByTag(H3);
                if (tempElements.size() > 0) {
                    mov.name = tempElements.first().text().trim();
                }
                tempElements = m.getElementsByClass(PHOTO);
                if (tempElements.size() > 0) {
                    // parsing of movie url
                    mov.url = BASE_URL + tempElements.first().attr("href").toString();
                    // parsing of movie poster
                    if (tempElements.first().getElementsByTag(IMG).size() > 0) {
                        mov.image = BASE_URL + tempElements.first().getElementsByTag(IMG).first().attr("src").toString();
                    }
                }
                // parsing of movie vote
                tempElements = m.getElementsByClass(RATING);
                if (tempElements.size() > 0) {
                    try {
                        String s = tempElements.first().ownText().replace(',', '.');
                        mov.vote = s.substring(0, s.length() - 1);
                    }
                    catch (Exception e) {
                        Log.e("KinoParser", e.getMessage());
                        mov.vote = "0";
                    }
                }
                array.add(mov);
            }
        }
        return array;
    }

    public static Movie parseMovie(Movie movie, String htmlStr) {
        return movie;
    }

    public static ArrayList<Cinema> parseCinemas(String htmlStr) {
        ArrayList<Cinema> array = new ArrayList<Cinema>();
        Document doc = Jsoup.parse(htmlStr);
        return array;
    }

    public static ArrayList<Comment> parseComments(String htmlStr) {
        ArrayList<Comment> array = new ArrayList<Comment>();
        Document doc = Jsoup.parse(htmlStr);
        return array;
    }

}
