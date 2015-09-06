package org.gbe.moviedbretrofit;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import moviedbretrofit.Movie;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieDtoConverter implements retrofit.Converter<Movie> {
    private static final String TAG = "MovieDtoConverter";

    @Override
    public Movie fromBody(ResponseBody responseBody) throws IOException {
        Movie dto = new Movie();
        String s = responseBody.toString();
        Log.v(TAG, s);
        return null;
    }

    @Override
    public RequestBody toBody(Movie movieDTO) {
        return null;
    }
}
