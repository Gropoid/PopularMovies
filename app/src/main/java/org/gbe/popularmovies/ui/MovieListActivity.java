package org.gbe.popularmovies.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import org.gbe.popularmovies.R;
import org.gbe.popularmovies.model.Movie;
import org.gbe.popularmovies.model.Review;
import org.gbe.popularmovies.model.Video;

public class MovieListActivity extends AppCompatActivity
        implements MovieListActivityInterface {

    private static final String TAG = "MainActivity";
    private static final String MOVIE_LIST_FRAGMENT = "MOVIE_LIST_FRAGMENT";
    private static final String MOVIE_DETAILS_FRAGMENT = "MOVIE_DETAILS_FRAGMENT";
    private static final String VIDEOS_FRAGMENT = "VIDEOS_FRAGMENT";
    private static final String REVIEWS_FRAGMENT = "REVIEWS_FRAGMENT";

    @Bind(R.id.movie_list_main_frame)
    FrameLayout flMainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        displayMovieListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void displayMovieListFragment() {
        if (getFragmentManager().findFragmentByTag(MOVIE_LIST_FRAGMENT) == null) {
            MovieListFragment f = MovieListFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.movie_list_main_frame, f, MOVIE_LIST_FRAGMENT).commit();
        }
    }

    public void displayVideoFragment(List<Video> videos) {
        VideoFragment f = VideoFragment.newInstance(videos);
        if (!isTablet()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_list_main_frame, f, VIDEOS_FRAGMENT)
                    .addToBackStack("le_videolist")
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_right_panel, f, VIDEOS_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void displayMovieDetailsFragment(Movie movie) {
        MovieDetailsFragment f = MovieDetailsFragment.newInstance(movie);
        if (!isTablet()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_list_main_frame, f, MOVIE_DETAILS_FRAGMENT)
                    .addToBackStack("le_movie")
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_right_panel, f, MOVIE_DETAILS_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }


    public void onVideoClicked(Video video) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void displayReviewsFragment(List<Review> reviews) {
        ReviewFragment f = ReviewFragment.newInstance(reviews);
        if (!isTablet()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_list_main_frame, f, REVIEWS_FRAGMENT)
                    .addToBackStack("le_reviews")
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_right_panel, f, REVIEWS_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
