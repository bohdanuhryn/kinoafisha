package com.bohdanuhryn.kinoafisha.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.adapters.MoviesAdapter;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.model.data.Movie;
import com.bohdanuhryn.kinoafisha.model.parameters.MovieSearchParams;
import com.bohdanuhryn.kinoafisha.model.responses.MoviesList;
import com.bohdanuhryn.food2fork.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class MoviesSearchFragment extends Fragment {

    public static final String TAG = "MoviesSearchFragment";

    private final String SAVED_SEARCH_PARAMS = "saved_movie_search_params";

    private View rootView;
    @Bind(R.id.movies_recycler)
    RecyclerView moviesRecycler;
    LinearLayoutManager moviesLinearLayoutManager;
    private MoviesAdapter moviesAdapter;
    @Bind(R.id.movies_swipe)
    SwipeRefreshLayout moviesSwipeRefreshLayout;

    @Bind(R.id.search_genre)
    Spinner genreSpinner;
    @Bind(R.id.search_year)
    Spinner yearSpinner;
    @Bind(R.id.search_text)
    EditText searchTextView;
    @Bind(R.id.search_button)
    ImageButton searchButton;
    @Bind(R.id.search_all_button)
    ImageButton searchAllButton;

    private String[] genresNamesArray;
    private int[] genresIdsArray;
    private ArrayList<String> yearsArray;

    private MovieSearchParams searchParams;
    private ArrayList<Movie> moviesArray;

    private OnMainFragmentListener mainFragmentListener;

    public static MoviesSearchFragment newInstance() {
        MoviesSearchFragment fragment = new MoviesSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movies_search, container, false);
        ButterKnife.bind(this, rootView);
        checkSearchParams(savedInstanceState);
        setupMoviesRecyclerView();
        setupMoviesAdapter();
        setupEndlessRecycler();
        setupMoviesSwipeRefreshLayout();
        setupSearchButton();
        setupSearchAllButton();
        setupGenresArrays();
        setupGenreSpinner();
        setupYearSpinner();
        reloadFilms();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainFragmentListener = (OnMainFragmentListener) context;
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

    private void setupMoviesRecyclerView() {
        moviesLinearLayoutManager = new LinearLayoutManager(getActivity());
        moviesRecycler.setLayoutManager(moviesLinearLayoutManager);
    }

    private void setupMoviesAdapter() {
        moviesAdapter = new MoviesAdapter(moviesArray, getActivity());
        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (mainFragmentListener != null) {
                    mainFragmentListener.startMovieActivity(moviesArray.get(position));
                }
            }
        });
        moviesRecycler.setAdapter(moviesAdapter);
    }

    private void setupEndlessRecycler() {
        moviesRecycler.setOnScrollListener(new EndlessRecyclerOnScrollListener(moviesLinearLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if (moviesArray.size() >= searchParams.offset) {
                    loadNewFilms();
                }
            }
        });
    }

    private void setupMoviesSwipeRefreshLayout() {
        moviesSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFilms();
            }
        });
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSpinner.getSelectedItemPosition() != 0
                        || yearSpinner.getSelectedItemPosition() != 0
                        || !searchTextView.getText().equals("")) {
                    searchParams.query.name = searchTextView.getText().toString();
                    reloadFilms();
                    searchAllButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupSearchAllButton() {
        searchAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchParams = new MovieSearchParams();
                searchTextView.setText("");
                genreSpinner.setSelection(0);
                yearSpinner.setSelection(0);
                reloadFilms();
                searchAllButton.setVisibility(View.GONE);
            }
        });
    }

    private void setupGenresArrays() {
        genresNamesArray = getResources().getStringArray(R.array.genres_names);
        genresIdsArray = getResources().getIntArray(R.array.genres_ids);
    }

    private void setupGenreSpinner() {
        genreSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genresNamesArray));
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    searchParams.query.genre = "";
                } else {
                    searchAllButton.setVisibility(View.VISIBLE);
                    searchParams.query.genre = String.valueOf(genresIdsArray[position]);
                }
                reloadFilms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupYearsArray() {
        yearsArray = new ArrayList<String>();
        yearsArray.add(getString(R.string.year_all));
        for (int i = 2020; i >= 2000; --i) {
            yearsArray.add(String.valueOf(i));
        }
        yearsArray.add(getString(R.string.year_bottom));
    }

    private void setupYearSpinner() {
        setupYearsArray();
        yearSpinner.setAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, yearsArray));
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    searchParams.query.year = "";
                } else if (position == yearsArray.size() - 1) {
                    searchAllButton.setVisibility(View.VISIBLE);
                    searchParams.query.year = "2000";
                } else {
                    searchAllButton.setVisibility(View.VISIBLE);
                    searchParams.query.year = yearsArray.get(position);
                }
                reloadFilms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void reloadFilms() {
        searchParams.offset = 0;
        moviesSwipeRefreshLayout.setRefreshing(true);
        KinoManager.getMoviesList(searchParams).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                MoviesList result = response.body();
                if (result.succes) {
                    moviesArray = result.result;
                    setupMoviesAdapter();
                    setupEndlessRecycler();
                }
                moviesSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(getActivity(), "Cannot reload data! " + t.getMessage(), Toast.LENGTH_LONG).show();
                moviesSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadNewFilms() {
        searchParams.offset += searchParams.limit;
        KinoManager.getMoviesList(searchParams).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                MoviesList moviesList = response.body();
                if (moviesList.succes) {
                    moviesArray.addAll(moviesList.result);
                    moviesAdapter.notifyDataSetChanged();
                }
                moviesSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                searchParams.offset -= searchParams.limit;
                Toast.makeText(getActivity(), "Cannot load new data! " + t.getMessage(), Toast.LENGTH_LONG).show();
                moviesSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void checkSearchParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            searchParams = (MovieSearchParams)savedInstanceState.getSerializable(SAVED_SEARCH_PARAMS);
        }
        if (searchParams == null) {
            searchParams = new MovieSearchParams();
        }
    }

    public interface OnMainFragmentListener {
        public void startMovieActivity(Movie movie);
    }
}
