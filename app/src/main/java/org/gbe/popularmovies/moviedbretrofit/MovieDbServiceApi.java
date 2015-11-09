package org.gbe.popularmovies.moviedbretrofit;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface MovieDbServiceApi {

     final class SORT_BY{
        public static final String POPULARITY_DESCENDING = "popularity.desc";
        public static final String POPULARITY_ASCENDING = "popularity.asc";
        public static final String VOTE_AVERAGE_DESCENDING = "vote_average.desc";
        public static final String VOTE_AVERAGE_ASCENDING = "vote_average.asc";
    }


    // https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=
    @GET("/3/discover/movie")
    Call<MovieDbResponseDTO> getMostPopularMovies(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy
    );

    @GET("/3/movie/{id}/videos")
    Call<MovieDbVideosDTO> getTrailers(
            @Path("id") long id,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/reviews")
    Call<MovieDbReviewsDTO> getReviews(
            @Path("id") long id,
            @Query("api_key") String apiKey
    );
}
