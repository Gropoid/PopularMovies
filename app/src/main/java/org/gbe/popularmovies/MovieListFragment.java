package org.gbe.popularmovies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import model.Movie;
import moviedbretrofit.MovieDbResponseDTO;
import moviedbretrofit.MovieDbServiceApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";
    private static final String MOST_POPULAR_MOVIES_KEY = "MostPopularMoviesKey";
    private static final String BEST_RATED_MOVIES_KEY = "BestRatedMoviesKey";
    private String movieDbUrl;
    private String imageDbUrl;

    private static final String IMAGE_SIZE = "w185";

    private static final int GRID_WIDTH = 2;

    @Bind(R.id.rvPosters)
    RecyclerView rvPosters;

    MovieListAdapter moviesAdapter;

    private String sortingCriterion;

    private static final String PREF_SORTING = "SORTING";

    private List<Movie> mostPopularMovies;
    private List<Movie> bestRatedMovies;
    private List<Movie> displayedMovies;

    private Call<MovieDbResponseDTO> mCallByPopularity;
    private Call<MovieDbResponseDTO> mCallByRatings;

    private String private_key; // in non-submitted res/values/private_key.xml

    MovieDbServiceApi movieDbService;

    public MovieListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDbUrl = getString(R.string.movie_db_url);
        imageDbUrl = getString(R.string.image_db_url);

        setHasOptionsMenu(true);

        bestRatedMovies = new ArrayList<>();
        mostPopularMovies = new ArrayList<>();
        displayedMovies = new ArrayList<>();

        if( savedInstanceState != null ) {
            // requires Parceler 0.2.7 or above to work :
            bestRatedMovies = Parcels.unwrap(savedInstanceState.getParcelable(BEST_RATED_MOVIES_KEY));
            mostPopularMovies = Parcels.unwrap(savedInstanceState.getParcelable(MOST_POPULAR_MOVIES_KEY));
        }

        private_key = getActivity().getResources().getString(R.string.themoviedbkey);
        movieDbService = new Retrofit.Builder()
                .baseUrl(movieDbUrl)
                .addConverter(
                        MovieDbResponseDTO.class,
                        GsonConverterFactory.create().get(MovieDbResponseDTO.class)
                )
                .build()
                .create(MovieDbServiceApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, v);
        moviesAdapter = new MovieListAdapter(getActivity(), displayedMovies, imageDbUrl + IMAGE_SIZE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvPosters.setLayoutManager(
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        } else {
            rvPosters.setLayoutManager(
                    new GridLayoutManager(getActivity(), GRID_WIDTH));
        }
        rvPosters.setAdapter(moviesAdapter);
        fetchData();
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);

        // requires Parceler 0.2.7 or above to work :
        b.putParcelable(MOST_POPULAR_MOVIES_KEY, Parcels.wrap(mostPopularMovies));
        b.putParcelable(BEST_RATED_MOVIES_KEY, Parcels.wrap(bestRatedMovies));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_by_popularity) {
            //swap lists
            sortingCriterion = MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING;
            applySortingCriterion();
        }
        if (id == R.id.sort_by_ratings) {
            sortingCriterion = MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING;
            applySortingCriterion();
        }
        if (id == R.id.debug_button) {
            fetchData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main_fragment, menu);
    }

    private void applySortingCriterion() {
        displayedMovies.clear();
        if (MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING.equals(sortingCriterion)) {
            displayedMovies.addAll(mostPopularMovies);
        }else if(MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING.equals(sortingCriterion)) {
            displayedMovies.addAll(bestRatedMovies);
        }
        moviesAdapter.notifyDataSetChanged();
    }

    void fetchMoviesByPopularity() {
        mCallByPopularity = movieDbService.getMostPopularMovies(private_key,
                        MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING);
        mCallByPopularity.enqueue(new Callback<MovieDbResponseDTO>() {
            @Override
            public void onResponse(Response<MovieDbResponseDTO> response) {
                if (getView() != null) {
                    mostPopularMovies.clear();
                    mostPopularMovies.addAll(response.body().getResults());
                    applySortingCriterion();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getView() != null) {
                    Toast.makeText(getActivity(),
                            "Failed retrieving movies by popularity. Please check your internet connection",
                            Toast.LENGTH_LONG).show();
                    applySortingCriterion();
                }
            }
        });
    }

    void fetchMoviesByRatings() {
        mCallByRatings = movieDbService.getMostPopularMovies(private_key, MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING);
        mCallByRatings.enqueue(new Callback<MovieDbResponseDTO>() {
            @Override
            public void onResponse(Response<MovieDbResponseDTO> response) {
                if (getView() != null) {
                    bestRatedMovies.clear();
                    bestRatedMovies.addAll(response.body().getResults());
                    applySortingCriterion();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getView() != null) {
                    Toast.makeText(getActivity(),
                            "Failed retrieving movies by ratings. Please check your internet connection",
                            Toast.LENGTH_LONG).show();
                    applySortingCriterion();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCallByPopularity != null)
            mCallByPopularity.cancel();
        if(mCallByRatings != null)
        mCallByRatings.cancel();
    }
    public void fetchData() {
        fetchMoviesByPopularity();
        fetchMoviesByRatings();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        sortingCriterion = prefs.getString(PREF_SORTING, MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING);
    }

    private void savePreferences() {
        SharedPreferences prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        prefs.edit().putString(PREF_SORTING, sortingCriterion).apply();
    }

}
