package org.gbe.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.gbe.model.Movie;
import org.gbe.moviedbretrofit.MovieDtoConverter;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import moviedbretrofit.MovieDTO;
import moviedbretrofit.MovieDbResponseDTO;
import moviedbretrofit.MovieDbServiceApi;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<Movie> movies;
    private MovieListAdapter moviesAdapter;
    private String key; // in non-submitted res/values/key.xml
    MovieDbServiceApi movieDbService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key = getBaseContext().getResources().getString(R.string.themoviedbkey);
        movieDbService = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverter(MovieDbResponseDTO.class, GsonConverterFactory.create().get(MovieDbResponseDTO.class))
                .build()
                .create(MovieDbServiceApi.class);
        ButterKnife.bind(this);
        //movieDbService.getMostPopularMovies(key, MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.debug_button)
    public void launchUpdate() {
        AsyncTask t = new FetchMoviesTask();
        String arg = MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING;
        t.execute(arg);
    }

    // TODO : Why can't I use String instead of Object ??
    private class FetchMoviesTask extends AsyncTask<Object, Void, MovieDbResponseDTO> {

        @Override
        protected MovieDbResponseDTO doInBackground(Object... params) {
            if ( movieDbService ==  null || params.length == 0) {
                return null;
            }
            Call<MovieDbResponseDTO> c = movieDbService.getMostPopularMovies(key, MovieDbServiceApi.SORT_BY.VOTE_AVERAGE_DESCENDING);
            try {
                Response<MovieDbResponseDTO> r =  c.execute();
                if (r.isSuccess()) {
                    MovieDbResponseDTO dto = r.body();
                    Log.v(TAG, "Succesfull call ! Response length : " + dto.getResults().size());
                    return r.body();
                } else {
                    return null;
                }
            } catch (IOException ex){
                Log.e(TAG, "Call execution failed!\n" + ex.getStackTrace().toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieDbResponseDTO result) {
            if (result != null) {
                for (MovieDTO dto : result.getResults()) {
                    Log.v(TAG, "Received Movie : " + dto.getTitle());
                }
            }
        }
    }
}
