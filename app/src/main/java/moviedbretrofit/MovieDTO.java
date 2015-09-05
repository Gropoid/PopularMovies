package moviedbretrofit;

import java.util.List;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieDTO {
//
//    {
//      "adult":false,
//      "backdrop_path":"/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg",
//      "genre_ids":[
//        53,
//        28,
//        12
//      ],
//      "id":76341,
//      "original_language":"en",
//      "original_title":"Mad Max: Fury Road",
//      "overview":"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.",
//      "release_date":"2015-05-15",
//      "poster_path":"/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
//      "popularity":49.489303,
//      "title":"Mad Max: Fury Road",
//      "video":false,
//      "vote_average":7.7,
//      "vote_count":2021
//    }
    Boolean adult;
    String backdropPath;
    List<Integer> genreIds;
    int id;
    String originalLanguage;
    String originalTitle;
    String overview;
    String releaseDate;
    String posterPath;
    float popularity;
    String title;
    Boolean video;
    float voteAverage;
    int voteCount;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
