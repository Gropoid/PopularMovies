package org.gbe.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import moviedbretrofit.Movie;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Activity mContext;
    private String baseMovieDbUrl;

    public MovieListAdapter(Activity context, List<Movie> movies, String baseMovieDbUrl) {
        this.movies = movies;
        this.mContext = context;
        this.baseMovieDbUrl = baseMovieDbUrl;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new MovieViewHolder(posterView, mContext);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder viewHolder, int position) {
        // Recycle view
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
            //TODO : add click handler here
        }
    }

    public void onMovieSelected(Movie movie) {
        Intent i = new Intent(mContext, MovieDetailsActivity.class);
        i.putExtra(MovieDetailsActivity.MOVIE_KEY, Parcels.wrap(movie));
        mContext.startActivity(i);
    }
}
