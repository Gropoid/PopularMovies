package org.gbe.popularmovies.data;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.Collection;

import org.gbe.popularmovies.model.Movie;

public class MovieDao extends BaseDaoImpl<Movie, Long> {

    protected MovieDao(Class<Movie> dataClass) throws SQLException {
        super(dataClass);
    }

    public Movie findById(Long id) throws SQLException {
        return queryForId(id);
    }

    public Collection<Movie> findAllFavorites() throws SQLException {
        return queryForEq("isFavorite", true);
    }

    @Override
    public int update(Movie data) throws SQLException {
        return super.update(data);
    }
}
