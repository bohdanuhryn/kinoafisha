package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.fragments.CommentsFragment;
import com.bohdanuhryn.kinoafisha.fragments.SessionsFragment;
import com.bohdanuhryn.kinoafisha.model.Movie;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private Movie movie;

    public MoviePagerAdapter(Context context, FragmentManager fm, Movie movie) {
        super(fm);
        this.context = context;
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return SessionsFragment.newInstance(movie.id);
            case 1:
                return CommentsFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return context.getString(R.string.cinemas);
            case 1:
                return context.getString(R.string.comments);
        }
        return "";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
