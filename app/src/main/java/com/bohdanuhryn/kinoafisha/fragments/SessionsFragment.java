package com.bohdanuhryn.kinoafisha.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.SessionsAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.model.Cinema;
import com.bohdanuhryn.kinoafisha.model.responses.SessionsList;

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
 * Created by BohdanUhryn on 11.03.2016.
 */
public class SessionsFragment extends Fragment {

    public static final String TAG = "SessionsFragment";

    private static final String FILM_ARG = "film_arg";

    private View rootView;
    @Bind(R.id.sessions_recycler)
    RecyclerView sessionsRecycler;
    private LinearLayoutManager sessionsLayoutManager;
    private SessionsAdapter sessionsAdapter;
    @Bind(R.id.sessions_date_button)
    ImageButton dateButton;
    @Bind(R.id.sessions_date)
    TextView dateView;

    private OnSessionsFragmentListener sessionsFragmentListener;

    private DatePickerDialog sessionsDateDialog;
    private SimpleDateFormat dateFormatter;

    private long film;
    private long date;
    private long city;
    private ArrayList<Cinema> sessionsArray;

    public static SessionsFragment newInstance(long film) {
        SessionsFragment fragment = new SessionsFragment();
        Bundle args = new Bundle();
        args.putLong(FILM_ARG, film);
        fragment.setArguments(args);
        return fragment;
    }

    public SessionsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sessions, container, false);
        ButterKnife.bind(this, rootView);
        readArguments();
        setupSessionsDateDialog();
        setupDateButton();
        setupSessionsRecyclerView();
        setupSessionsAdapter();
        reloadSessions();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sessionsFragmentListener = (OnSessionsFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSessionsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sessionsFragmentListener = null;
    }

    private void readArguments() {
        Bundle args = getArguments();
        if (args != null) {
            film = args.getLong(FILM_ARG, 0);
        }
    }

    private void setupSessionsDateDialog() {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        date = current.getTimeInMillis() / 1000;
        dateView.setText(dateFormatter.format(current.getTime()));
        sessionsDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                newDate.set(Calendar.HOUR_OF_DAY, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                newDate.set(Calendar.MILLISECOND, 0);
                date = newDate.getTimeInMillis() / 1000;
                dateView.setText(dateFormatter.format(newDate.getTime()));
                reloadSessions();
            }
        }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
    }

    private void setupDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionsDateDialog.show();
            }
        });
    }

    private void setupSessionsRecyclerView() {
        sessionsLayoutManager = new LinearLayoutManager(getActivity());
        sessionsRecycler.setLayoutManager(sessionsLayoutManager);
    }

    private void setupSessionsAdapter() {
        sessionsAdapter = new SessionsAdapter(sessionsArray, getActivity());
        sessionsAdapter.setOnSessionsFragmentListener(sessionsFragmentListener);
        sessionsRecycler.setAdapter(sessionsAdapter);
    }

    private void reloadSessions() {
        KinoManager.getMovieSessionsList(film, date, 8/*city*/).enqueue(new Callback<SessionsList>() {
            @Override
            public void onResponse(Call<SessionsList> call, Response<SessionsList> response) {
                SessionsList result = response.body();
                if (result.succes) {
                    sessionsArray = new ArrayList<Cinema>();
                    for (int i = 0; i < result.result.size(); ++i) {
                        sessionsArray.add(result.result.get(i).k);
                    }
                    setupSessionsAdapter();
                }
            }

            @Override
            public void onFailure(Call<SessionsList> call, Throwable t) {

            }
        });
    }

    public interface OnSessionsFragmentListener {
        void onMapActivityStart(Cinema cinema);
    }

}
