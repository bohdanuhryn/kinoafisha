package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.fragments.CinemasFragment;
import com.bohdanuhryn.kinoafisha.fragments.CommentsFragment;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MoviePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return CinemasFragment.newInstance();
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
