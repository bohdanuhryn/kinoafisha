package com.bohdanuhryn.kinoafisha.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.model.data.City;

import java.util.List;

/**
 * Created by BohdanUhryn on 19.03.2016.
 */
public class CitiesAdapter extends ArrayAdapter<City> {

    public CitiesAdapter(Context context, int resource, List<City> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        City item = getItem(position);
        convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView t = (TextView)convertView.findViewById(android.R.id.text1);
        t.setText(item.name);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        City item = getItem(position);
        convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView t = (TextView)convertView.findViewById(android.R.id.text1);
        t.setText(item.name);
        return convertView;
    }
}
