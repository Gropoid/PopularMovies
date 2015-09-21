package org.gbe.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import moviedbretrofit.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String MY_MOVIE_KEY = "my_movie_key";
    private static final String IMAGE_SIZE = "w185";

    private String imageDbUrl;
    private Movie mMovie;

    @Bind(R.id.tv_details_title)
    TextView tvTitle;

    @Bind(R.id.tv_details_ratings_average)
    TextView tvRatingsAverage;

    @Bind(R.id.tv_details_release_date)
    TextView tvReleaseDate;

    @Bind(R.id.tv_details_synopsis)
    TextView tvSynopsis;

    @Bind(R.id.iv_details_poster)
    ImageView ivPoster;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, v);
        imageDbUrl = getString(R.string.image_db_url);
        if (savedInstanceState == null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        } else {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        }
        assignFields();
        return v;
    }

    private void assignFields() {
        if(mMovie == null) {
            throw new NullPointerException("mMovie was never assigned");
        }
        tvTitle.setText(mMovie.getTitle());
        tvRatingsAverage.setText("avg : " + mMovie.getVote_average() + "/10");
        tvReleaseDate.setText("Released : " + mMovie.getRelease_date());
        tvSynopsis.setText(mMovie.getOverview());
        Picasso.with(getActivity())
                .load(imageDbUrl + IMAGE_SIZE + mMovie.getPoster_path())
                .error(R.drawable.videostatic)
                .placeholder(R.drawable.progress_wheel_animation)
                .fit()
                .centerInside()
                .noFade()
                .into(ivPoster);
    }

    public static MovieDetailsFragment newInstance(Movie m) {
        Bundle b = new Bundle();
        b.putParcelable(MY_MOVIE_KEY, Parcels.wrap(m));
        MovieDetailsFragment f = new MovieDetailsFragment();
        f.setArguments(b);
        return f;
    }
}
