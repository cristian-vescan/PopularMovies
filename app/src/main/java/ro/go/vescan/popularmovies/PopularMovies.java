package ro.go.vescan.popularmovies;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ro.go.vescan.popularmovies.model.Movie;

public class PopularMovies extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<Movie>>

{
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        // restore save instace
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<>();
        }
        else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
        // initialize grid view
        movieAdapter = new MovieAdapter(this,  movieList);
        GridView gridView = findViewById(R.id.gvMovies);
        gridView.setAdapter(movieAdapter);

        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putBoolean(MovieAsyncLoader.MOST_POPULAR_KEY, true);
        getSupportLoaderManager().initLoader(1, bundleForLoader, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId())
        {
            case R.id.popular_mi :
                showPopularMovies();
                return true;
            case R.id.topRated_mi :
                showTopRatedMovies();
                return true;
        }
        return false;
    }

    void showPopularMovies()
    {
        setTitle(getString(R.string.popular_menu_item));
        // we want most popular movies
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putBoolean(MovieAsyncLoader.MOST_POPULAR_KEY, true);
        getSupportLoaderManager().restartLoader(1, bundleForLoader, this);
    }
    void showTopRatedMovies()
    {
        setTitle(getString(R.string.top_rated_menu_item));
        // we want top rated movies
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putBoolean(MovieAsyncLoader.MOST_POPULAR_KEY, false);
        getSupportLoaderManager().restartLoader(1, bundleForLoader, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieAsyncLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        movieList.clear();
        if (data!= null)
          movieList.addAll(data);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
