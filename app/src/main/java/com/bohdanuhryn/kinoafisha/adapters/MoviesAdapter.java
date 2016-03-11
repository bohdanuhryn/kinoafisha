package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 10.03.2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private OnItemClickListener itemClickListener;

    public MoviesAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = null;
        if (movies != null) {
            item = movies.get(position);
        }
        if (item != null && holder != null) {
            holder.nameView.setText(item.name);
            Picasso.with(context).load(item.smallPosterUrl).into(holder.posterView);
            holder.ratingView.setText(String.format("%.1f", item.rating));
            holder.ratingBarView.setRating(item.rating / 10);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.movie_name)
        TextView nameView;
        @Bind(R.id.movie_poster)
        ImageView posterView;
        @Bind(R.id.movie_rating_bar)
        RatingBar ratingBarView;
        @Bind(R.id.movie_rating)
        TextView ratingView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onClick(View v, int position);
    }

}
