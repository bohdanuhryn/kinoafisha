package com.bohdanuhryn.kinoafisha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MainPagerAdapter;
import com.bohdanuhryn.kinoafisha.adapters.MoviePagerAdapter;
import com.bohdanuhryn.kinoafisha.model.data.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    private View rootView;

    @Bind(R.id.main_pager)
    ViewPager mainPager;
    @Bind(R.id.main_tabs)
    TabLayout mainTabs;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setupMainPager();
        return rootView;
    }

    private void setupMainPager() {
        MainPagerAdapter adapter = new MainPagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        mainTabs.setTabsFromPagerAdapter(adapter);
        mainPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabs));
        mainTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public interface OnMainFragmentListener {
        public void startMovieActivity(Movie movie);
    }
}
