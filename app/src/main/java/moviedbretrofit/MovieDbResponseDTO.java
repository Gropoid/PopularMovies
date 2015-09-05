package moviedbretrofit;

import java.util.List;

/**
 * Created by gbe on 9/5/15.
 */
public class MovieDbResponseDTO {
    int page;
    int totalPages;
    int totalResults;
    List<MovieDTO> results;

    public MovieDbResponseDTO() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieDTO> getResults() {
        return results;
    }

    public void setResults(List<MovieDTO> results) {
        this.results = results;
    }
}
