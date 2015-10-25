package org.gbe.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.DatabaseHelper;
import data.MovieDao;
import model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String MY_MOVIE_KEY = "my_movie_key";
    private static final String IMAGE_SIZE = "w185";
    private static final String TAG = "MovieDetailsFragment";

    private String imageDbUrl;
    private Movie mMovie;

    DatabaseHelper dbHelper;
    MovieDao movieDao;

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

    @Bind(R.id.tb_favorite)
    ToggleButton tbFavorite;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
            //dbHelper = new DatabaseHelper(getActivity());
            movieDao = dbHelper.getMovieDao();
        } catch (SQLException exc) {
            handleSQLError(exc);
        }
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, v);
        imageDbUrl = getString(R.string.image_db_url);
        if (savedInstanceState == null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        } else {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        }
        getMovieInfoFromDb();
        assignFields();
        return v;
    }

    private void getMovieInfoFromDb() {
        Movie dbMovie;
        try {
            dbMovie = movieDao.findById(mMovie.getId());
            if (dbMovie != null) {
                mMovie = dbMovie;
            }
        } catch (SQLException e) {
            handleSQLError(e);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OpenHelperManager.releaseHelper();
    }

    private void handleSQLError(SQLException exc) {
        Log.e(TAG, exc.getLocalizedMessage() + "\n" + exc.getSQLState());
        Toast.makeText(getActivity(), R.string.sql_error_message, Toast.LENGTH_LONG).show();
    }

    private void assignFields() {

        if(mMovie == null) {
            throw new NullPointerException("mMovie was never assigned");
        }
        tvTitle.setText(mMovie.getTitle());
        tvRatingsAverage.setText("avg : " + mMovie.getVote_average() + "/10");
        tvReleaseDate.setText("Released : " + mMovie.getRelease_date());
        tvSynopsis.setText(mMovie.getOverview());
        tbFavorite.setChecked(mMovie.getIsFavorite());
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

    @OnClick(R.id.tb_favorite)
    void onFavoriteClicked() {
        mMovie.setIsFavorite(tbFavorite.isChecked());
        try {
            movieDao.createOrUpdate(mMovie);
        } catch (SQLException e) {
            handleSQLError(e);
        }
    }
}
