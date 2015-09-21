package org.gbe.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.parceler.Parcels;

import butterknife.Bind;
import moviedbretrofit.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "movie";

    @Bind(R.id.fr_movie_details_fragment)
    View frMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {
            Movie m = Parcels.unwrap(getIntent().getExtras().getParcelable(MOVIE_KEY));
            MovieDetailsFragment f = MovieDetailsFragment.newInstance(m);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fr_movie_details_fragment, f)
                    .commit();
        }
    }

}
