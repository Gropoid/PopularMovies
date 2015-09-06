package moviedbretrofit;

import java.util.List;

/**
 * Created by gbe on 9/5/15.
 */
public class Movie {
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
    String backdrop_path;
    List<Integer> genre_ids;
    int id;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    float popularity;
    String title;
    Boolean video;
    float vote_average;
    int vote_count;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
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

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
