package org.gbe.moviedbretrofit;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import moviedbretrofit.MovieDTO;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieDtoConverter implements retrofit.Converter<MovieDTO> {
    private static final String TAG = "MovieDtoConverter";

    @Override
    public MovieDTO fromBody(ResponseBody responseBody) throws IOException {
        MovieDTO dto = new MovieDTO();
        String s = responseBody.toString();
        Log.v(TAG, s);
        return null;
    }

    @Override
    public RequestBody toBody(MovieDTO movieDTO) {
        return null;
    }
}
