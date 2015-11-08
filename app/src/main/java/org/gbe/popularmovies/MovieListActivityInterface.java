package org.gbe.popularmovies;

import java.util.List;

import model.Movie;
import model.Video;

public interface MovieListActivityInterface {
    void displayVideoFragment(List<Video> videos);
    void displayMovieDetailsFragment(Movie movie);
    void onVideoClicked(Video video);
}
