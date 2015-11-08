package org.gbe.popularmovies;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.DatabaseHelper;
import data.MovieDao;
import model.Movie;
import model.Review;
import model.Video;
import moviedbretrofit.MovieDbReviewsDTO;
import moviedbretrofit.MovieDbService;
import moviedbretrofit.MovieDbServiceApi;
import moviedbretrofit.MovieDbVideosDTO;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

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

    private MenuItem miPlayVideo;
    private MenuItem miReadReviews;

    private List<Video> videos;

    private String private_key;
    private MovieDbServiceApi movieDbService;

    private MovieListActivity hostActivity;
    private List<Review> reviews;


    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
            movieDao = dbHelper.getMovieDao();
        } catch (SQLException exc) {
            handleSQLError(exc);
        }
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, v);
        imageDbUrl = getString(R.string.image_db_url);
        private_key = getString(R.string.themoviedbkey);
        movieDbService = MovieDbService.getInstance(getActivity());

        if (savedInstanceState == null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        } else {
            mMovie = Parcels.unwrap(getArguments().getParcelable(MY_MOVIE_KEY));
        }
        getMovieInfoFromDb();
        videos = new ArrayList<>();
        fetchVideos();
        reviews = new ArrayList<>();
        fetchReviews();
        assignFields();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            hostActivity = (MovieListActivity)activity;
        } catch ( ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must be a MovieListActivity");
        }
    }

    private void fetchVideos() {
        Call<MovieDbVideosDTO> videoListCall = movieDbService.getTrailers(mMovie.getId(), private_key);
        videoListCall.enqueue(new Callback<MovieDbVideosDTO>() {
            @Override
            public void onResponse(Response<MovieDbVideosDTO> response) {
                MovieDbVideosDTO dto = response.body();
                if (dto != null) {
                    List<Video> videoList = dto.getResults();
                    if (videoList != null) {
                        if (getView() != null) {
                            videos.clear();
                            videos.addAll(videoList);
                            if (videos.size() > 0) {
                                showPlayVideoMenuItem();
                                return;
                            }
                        }
                    }
                }
                hidePlayVideoMenuItem();
            }

            @Override
            public void onFailure(Throwable t) {
                if (getView() != null) {
                    Toast.makeText(getActivity(), "Error retrieving video list", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error retrieving video list", t);
                    hidePlayVideoMenuItem();
                }
            }
        });
    }

    private void fetchReviews() {
        Call<MovieDbReviewsDTO> reviewListCall = movieDbService.getReviews(mMovie.getId(), private_key);
        reviewListCall.enqueue(new Callback<MovieDbReviewsDTO>() {
            @Override
            public void onResponse(Response<MovieDbReviewsDTO> response) {
                MovieDbReviewsDTO dto = response.body();
                if ( dto != null){
                    List<Review> reviewList = dto.getResults();
                    if (reviewList != null) {
                        if (getView() != null) {
                            reviews.clear();
                            reviews.addAll(reviewList);
                            if (reviews.size() > 0) {
                                showReadReviewsMenuItem();
                                return;
                            }
                        }
                    }
                }
                hideReadReviewsMenuItem();
            }

            @Override
            public void onFailure(Throwable t) {
                if (getView() != null) {
                    Toast.makeText(getActivity(), "Error retrieving reviews list", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error retrieving reviews list", t);
                    hideReadReviewsMenuItem();
                }
            }
        });
    }

    private void hideReadReviewsMenuItem() {
        if(miReadReviews != null) {
            miReadReviews.setVisible(false);
        }
    }

    private void showReadReviewsMenuItem() {
        if (miReadReviews != null ) {
            miReadReviews.setVisible(true);
        }
    }

    private void showPlayVideoMenuItem() {
        if (miPlayVideo != null ) {
            miPlayVideo.setVisible(true);
        }
    }

    private void hidePlayVideoMenuItem() {
        if (miPlayVideo != null ) {
            miPlayVideo.setVisible(false);
        }
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
        if (mMovie == null) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.play_videos) {
            hostActivity.displayVideoFragment(videos);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_movie_details, menu);
        miPlayVideo = menu.findItem(R.id.play_videos);
        miReadReviews = menu.findItem(R.id.read_reviews);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }

}
