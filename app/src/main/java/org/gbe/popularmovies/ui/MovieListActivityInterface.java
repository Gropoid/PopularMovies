package org.gbe.popularmovies.ui;

import java.util.List;

import org.gbe.popularmovies.model.Movie;
import org.gbe.popularmovies.model.Video;

public interface MovieListActivityInterface {
    void displayVideoFragment(List<Video> videos);
    void displayMovieDetailsFragment(Movie movie);
    void onVideoClicked(Video video);
}
