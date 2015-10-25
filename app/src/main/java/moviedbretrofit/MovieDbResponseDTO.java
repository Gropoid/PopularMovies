package moviedbretrofit;

import java.util.List;

import model.Movie;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieDbResponseDTO {
    int page;
    int total_pages;
    int total_results;
    List<Movie> results;

    public MovieDbResponseDTO() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
