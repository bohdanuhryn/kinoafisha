package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.fragments.CommentsFragment;
import com.bohdanuhryn.kinoafisha.fragments.SessionsFragment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private Movie movie;

    private CommentsFragment commentsFragment;
    private SessionsFragment sessionsFragment;

    public MoviePagerAdapter(Context context, FragmentManager fm, Movie movie) {
        super(fm);
        this.context = context;
        this.movie = movie;
        this.commentsFragment = CommentsFragment.newInstance(movie);
        this.sessionsFragment = SessionsFragment.newInstance(movie.id);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return sessionsFragment;
            case 1:
                return commentsFragment;
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
