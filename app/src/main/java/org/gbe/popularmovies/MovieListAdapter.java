package org.gbe.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieListAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static final class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView coverImageView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            coverImageView = null;
        }


    }
}
