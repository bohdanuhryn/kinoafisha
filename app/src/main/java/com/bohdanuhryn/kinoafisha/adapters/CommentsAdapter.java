package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.client.KinoManager;
import com.bohdanuhryn.kinoafisha.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
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
            Picasso.with(context).load(KinoManager.getFullUrl(item.avatar)).into(holder.avatarView);
            holder.authorView.setText(item.author);
            holder.dateView.setText(item.date);
            holder.descriptionView.setText(item.description);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_comment, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.comment_avatar)
        ImageView avatarView;
        @Bind(R.id.comment_author)
        TextView authorView;
        @Bind(R.id.comment_date)
        TextView dateView;
        @Bind(R.id.comment_description)
        TextView descriptionView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
