package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.gbe.popularmovies.R;

import java.sql.SQLException;

import model.Movie;

/**
 * Created by geraud on 25/10/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    private MovieDao _movieDao;

    private static final String DATABASE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Movie.class);
        } catch (SQLException e) {
            Log.e(TAG, "Could not create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Movie.class, true);
            onCreate(database, connectionSource);
        } catch ( SQLException e) {
            Log.e(TAG, "Could not upgrade database");
        }
    }

    public MovieDao getMovieDao() throws SQLException {
        if (_movieDao == null) {
            _movieDao = new MovieDao(Movie.class);
            _movieDao.setConnectionSource(getConnectionSource());
            _movieDao.initialize();
        }
        return _movieDao;
    }


}
