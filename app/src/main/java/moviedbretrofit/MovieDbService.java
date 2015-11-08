package moviedbretrofit;

import android.content.Context;

import org.gbe.popularmovies.R;

import model.Movie;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MovieDbService {

    private static MovieDbServiceApi _movieDbService;

    public static MovieDbServiceApi getInstance(Context context) {
        if (_movieDbService == null) {
            _movieDbService = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.movie_db_url))
                    .addConverter(
                            MovieDbResponseDTO.class,
                            GsonConverterFactory.create().get(MovieDbResponseDTO.class)
                    )
                    .addConverter(
                            MovieDbVideosDTO.class,
                            GsonConverterFactory.create().get(MovieDbVideosDTO.class)
                    )
                    .addConverter(
                            MovieDbReviewsDTO.class,
                            GsonConverterFactory.create().get(MovieDbReviewsDTO.class)
                    )
                    .build().create(MovieDbServiceApi.class);
        }
        return _movieDbService;
    }
}
