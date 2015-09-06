package org.gbe.popularmovies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import moviedbretrofit.Movie;
import moviedbretrofit.MovieDbResponseDTO;
import moviedbretrofit.MovieDbServiceApi;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";
    private static final String MOVIE_DB_URL = "https://api.themoviedb.org";
    private static final String IMAGE_DB_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    private static final int GRID_WIDTH = 2;

    OnMovieSelectedListener mCallback;

    @Bind(R.id.rvPosters)
    RecyclerView rvPosters;

    MovieListAdapter moviesAdapter;

    private String sortingCriterion;

    private static final String PREF_SORTING = "SORTING";

    private List<Movie> mostPopularMovies;
    private List<Movie> bestRatedMovies;
    private List<Movie> displayedMovies;

    private String private_key; // in non-submitted res/values/private_key.xml

    MovieDbServiceApi movieDbService;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            mCallback = (OnMovieSelectedListener)activity;
        }
        catch(ClassCastException ex)
        {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener.");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bestRatedMovies = new ArrayList<>();
        mostPopularMovies = new ArrayList<>();
        displayedMovies = new ArrayList<>();

        private_key = getActivity().getResources().getString(R.string.themoviedbkey);
        movieDbService = new Retrofit.Builder()
                .baseUrl(MOVIE_DB_URL)
                .addConverter(
                        MovieDbResponseDTO.class,
                        GsonConverterFactory.create().get(MovieDbResponseDTO.class)
                )
                .build()
                .create(MovieDbServiceApi.class);
        //launchUpdate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        moviesAdapter = new MovieListAdapter(getActivity(), displayedMovies, IMAGE_DB_URL + IMAGE_SIZE);
        final RecyclerView.LayoutManager layout  = new GridLayoutManager(getActivity(), GRID_WIDTH);
        rvPosters.setLayoutManager(layout);
        rvPosters.setAdapter(moviesAdapter);
        launchUpdate();
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
            launchUpdate();
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

    // TODO : Why can't I use String instead of Object ??
    private class FetchMoviesTask extends AsyncTask<Object, Void, List<MovieDbResponseDTO>> {

        @Override
        protected List<MovieDbResponseDTO> doInBackground(Object... params) {
            if ( movieDbService ==  null) {
                return null;
            }
            List<MovieDbResponseDTO> responses = new ArrayList<MovieDbResponseDTO>();
            // TODO : switch to async calls
            Call<MovieDbResponseDTO> c0 = movieDbService.getMostPopularMovies(
                    private_key,
                    MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING);
            Call<MovieDbResponseDTO> c1 = movieDbService.getMostPopularMovies(
                    private_key,
                    MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING);
            try {
                Response<MovieDbResponseDTO> r =  c0.execute();
                if (r.isSuccess()) {
                    MovieDbResponseDTO dto = r.body();
                    Log.v(TAG, "Succesfull call ! Response length : " + dto.getResults().size());
                    responses.add(0, dto);
                } else {
                    responses.add(0, null);
                }
            } catch (IOException ex){
                Log.e(TAG, "Call execution failed!\n" + ex.getStackTrace().toString());
                responses.add(0, null);
            }

            try {
                Response<MovieDbResponseDTO> r =  c1.execute();
                if (r.isSuccess()) {
                    MovieDbResponseDTO dto = r.body();
                    Log.v(TAG, "Succesfull call ! Response length : " + dto.getResults().size());
                    responses.add(1, dto);
                } else {
                    responses.add(1, null);
                }
            } catch (IOException ex){
                Log.e(TAG, "Call execution failed!\n" + ex.getStackTrace().toString());
                responses.add(1, null);
            }
            return responses;
        }

        @Override
        protected void onPostExecute(List<MovieDbResponseDTO> results) {
            if (results != null) {
                // 0 == sorted by ratings, 1 == sorted by popularity
                bestRatedMovies.clear();
                for (moviedbretrofit.Movie dto : results.get(0).getResults()) {
                    Log.v(TAG, "Received Movie : " + dto.getTitle());
                }
                bestRatedMovies.addAll(results.get(0).getResults());
                mostPopularMovies.clear();
                for (moviedbretrofit.Movie dto : results.get(1).getResults()) {
                    Log.v(TAG, "Received Movie : " + dto.getTitle());
                }
                mostPopularMovies.addAll(results.get(1).getResults());
                applySortingCriterion();
            }
        }
    }

    public void launchUpdate() {
        AsyncTask t = new FetchMoviesTask();
        String arg = MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING;
        t.execute();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        sortingCriterion = prefs.getString(PREF_SORTING, MovieDbServiceApi.SORT_BY.POPULARITY_DESCENDING);
    }

    private void savePreferences() {
        SharedPreferences prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        prefs.edit().putString(PREF_SORTING, sortingCriterion).apply();
    }

    public interface OnMovieSelectedListener {
        void onMovieSelected(Movie movie);
    }
}
