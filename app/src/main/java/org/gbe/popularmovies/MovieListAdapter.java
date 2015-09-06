package org.gbe.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import moviedbretrofit.Movie;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Activity mContext;
    private String baseMovieDbUrl;

    public MovieListAdapter(Activity mContext, List<Movie> movies, String baseMovieDbUrl) {
        this.movies = movies;
        this.mContext = mContext;
        this.baseMovieDbUrl = baseMovieDbUrl;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new MovieViewHolder(posterView, mContext);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        // Recycle view
        Movie movie = movies.get(position);
        viewHolder.rootView.setTag(movie);
        Picasso.with(mContext).load(baseMovieDbUrl + movie.getPoster_path()).into(viewHolder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static final class MovieViewHolder extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivPoster;
        public MovieViewHolder(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivPoster = (ImageView)itemView.findViewById(R.id.ivPoster);
            //TODO : add click handler here
        }
    }
}
