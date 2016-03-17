package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.model.Comment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 17.03.2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Comment> comments;

    public CommentsAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment item = null;
        if (comments != null) {
            item = comments.get(position);
        }
        if (item != null && holder != null) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_comment, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
