package com.bohdanuhryn.kinoafisha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.kinoafisha.R;

/**
 * Created by BohdanUhryn on 11.03.2016.
 */
public class CinemasFragment extends Fragment {

    public static final String TAG = "CinamasFragment";

    private View rootView;

    public static CinemasFragment newInstance() {
        CinemasFragment fragment = new CinemasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CinemasFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cinemas, container, false);
        return rootView;
    }

}
