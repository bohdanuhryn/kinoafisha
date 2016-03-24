package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.fragments.CommentsFragment;
import com.bohdanuhryn.kinoafisha.fragments.DetailsFragment;
import com.bohdanuhryn.kinoafisha.fragments.KinoafishaFragment;
import com.bohdanuhryn.kinoafisha.fragments.MoviesSearchFragment;
import com.bohdanuhryn.kinoafisha.fragments.SessionsFragment;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return KinoafishaFragment.newInstance();
            case 1:
                return MoviesSearchFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return context.getString(R.string.kinoafisha);
            case 1:
                return context.getString(R.string.catlog);
        }
        return "";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
