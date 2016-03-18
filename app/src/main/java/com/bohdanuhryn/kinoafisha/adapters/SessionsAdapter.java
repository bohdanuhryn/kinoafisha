package com.bohdanuhryn.kinoafisha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bohdanuhryn.kinoafisha.R;
import com.bohdanuhryn.kinoafisha.fragments.SessionsFragment;
import com.bohdanuhryn.kinoafisha.model.Cinema;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 17.03.2016.
 */
public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Cinema> sessions;
    private SessionsFragment.OnSessionsFragmentListener sessionsFragmentListener;

    public SessionsAdapter(ArrayList<Cinema> sessions, Context context) {
        this.sessions = sessions;
        this.context = context;
    }

    public void setOnSessionsFragmentListener(SessionsFragment.OnSessionsFragmentListener sessionsFragmentListener) {
        this.sessionsFragmentListener = sessionsFragmentListener;
    }

    @Override
    public int getItemCount() {
        return sessions != null ? sessions.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cinema item = null;
        if (sessions != null) {
            item = sessions.get(position);
        }
        if (item != null && holder != null) {
            holder.nameView.setText(item.name);
            String sessionsStr = "";
            if (item.h != null) {
                for (int i = 0; i < item.h.size(); ++i) {
                    sessionsStr += item.h.get(i).name + "......" + item.h.get(i).sessions + "<br>";
                }
            }
            holder.sessionsView.setText(Html.fromHtml(sessionsStr));
            final Cinema cinema = item;
            holder.mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sessionsFragmentListener != null) {
                        sessionsFragmentListener.onMapActivityStart(cinema);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cinema_sessions, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cinema_name)
        TextView nameView;
        @Bind(R.id.cinema_sessions_list)
        TextView sessionsView;
        @Bind(R.id.cinema_map_button)
        ImageButton mapButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
