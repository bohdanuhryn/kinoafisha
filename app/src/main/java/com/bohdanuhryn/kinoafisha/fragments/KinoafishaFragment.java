package com.bohdanuhryn.kinoafisha.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bohdanuhryn.food2fork.utils.EndlessRecyclerOnScrollListener;
import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.CitiesAdapter;
import com.bohdanuhryn.kinoafisha.adapters.MoviesAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.helpers.CitiesHelper;
import com.bohdanuhryn.kinoafisha.model.data.City;
import com.bohdanuhryn.kinoafisha.model.data.Movie;
import com.bohdanuhryn.kinoafisha.model.responses.MoviesList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class KinoafishaFragment extends Fragment {

    public static final String TAG = "KinoafishaFragment";

    private View rootView;
    @Bind(R.id.kinoafisha_recycler)
    RecyclerView kinoafishaRecycler;
    private LinearLayoutManager linearLayoutManager;
    private MoviesAdapter kinoafishaAdapter;
    @Bind(R.id.kinoafisha_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.kinoafisha_date_button)
    ImageButton dateButton;
    @Bind(R.id.kinoafisha_date)
    TextView dateView;
    @Bind(R.id.kinoafisha_city)
    Spinner citySpinner;

    private DatePickerDialog dateDialog;
    private SimpleDateFormat dateFormatter;

    private int limit = 9999;
    private int offset;
    private String date;
    private long city;
    private ArrayList<Movie> kinoafishaArray;
    private ArrayList<City> citiesArray;

    private MainFragment.OnMainFragmentListener mainFragmentListener;

    public static KinoafishaFragment newInstance() {
        KinoafishaFragment fragment = new KinoafishaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_kinoafisha, container, false);
        ButterKnife.bind(this, rootView);
        setupKinoafishaRecyclerView();
        setupKinoafishaAdapter();
        setupSwipeRefreshLayout();
        setupCitySpinner();
        setupDateDialog();
        setupDateButton();
        reloadFilms();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainFragmentListener = (MainFragment.OnMainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainFragmentListener = null;
    }

    private void setupKinoafishaRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        kinoafishaRecycler.setLayoutManager(linearLayoutManager);
    }

    private void setupKinoafishaAdapter() {
        kinoafishaAdapter = new MoviesAdapter(kinoafishaArray, getActivity());
        kinoafishaAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (mainFragmentListener != null) {
                    mainFragmentListener.startMovieActivity(kinoafishaArray.get(position));
                }
            }
        });
        kinoafishaRecycler.setAdapter(kinoafishaAdapter);
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFilms();
            }
        });
    }

    private void setupCitySpinner() {
        citiesArray = CitiesHelper.getCities(getActivity());
        citySpinner.setAdapter(new CitiesAdapter(getActivity(), android.R.layout.simple_spinner_item, citiesArray));
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = citiesArray.get(position).id;
                reloadFilms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int nearestCity = CitiesHelper.getNearestCity(getActivity());
        citySpinner.setSelection(nearestCity);
        city = citiesArray.get(nearestCity).id;
    }

    private void setupDateDialog() {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        date = dateFormatter.format(current.getTime());
        dateView.setText(dateFormatter.format(current.getTime()));
        dateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                newDate.set(Calendar.HOUR_OF_DAY, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                newDate.set(Calendar.MILLISECOND, 0);
                date = dateFormatter.format(newDate.getTime());
                dateView.setText(dateFormatter.format(newDate.getTime()));
                reloadFilms();
            }
        }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
    }

    private void setupDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    }

    private void reloadFilms() {
        offset = 0;
        swipeRefreshLayout.setRefreshing(true);
        KinoManager.getKinoafishaList(limit, offset, date, city).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                MoviesList result = response.body();
                if (result.succes) {
                    kinoafishaArray = result.result;
                    setupKinoafishaAdapter();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(getActivity(), "Cannot reload data! " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
