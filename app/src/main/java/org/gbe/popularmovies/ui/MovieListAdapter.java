package org.gbe.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


import java.util.List;

import org.gbe.popularmovies.R;
import org.gbe.popularmovies.model.Movie;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private MovieListActivity mContext;
    private String baseMovieDbUrl;

    public MovieListAdapter(MovieListActivity context, List<Movie> movies, String baseMovieDbUrl) {
        this.movies = movies;
        this.baseMovieDbUrl = baseMovieDbUrl;
        this.mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new MovieViewHolder(posterView, mContext);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder viewHolder, int position) {
        Movie movie = movies.get(position);
        viewHolder.rootView.setTag(movie);
        Picasso.with(mContext)
                .load(baseMovieDbUrl + movie.getPoster_path())
                .error(R.drawable.videostatic)
                .placeholder(R.drawable.progress_wheel_animation)
                .fit()
                .noFade()
                .centerInside()
                .into(viewHolder.ivPoster);
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Movie m = (Movie)viewHolder.rootView.getTag();
                onMovieSelected(m);
            }
        });
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
        }
    }

    public void onMovieSelected(Movie movie) {
        mContext.displayMovieDetailsFragment(movie);
    }
}
