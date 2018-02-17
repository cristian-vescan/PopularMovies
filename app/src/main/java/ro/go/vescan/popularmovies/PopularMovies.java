package ro.go.vescan.popularmovies;

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

import ro.go.vescan.popularmovies.model.Movie;

public class PopularMovies extends AppCompatActivity {
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    Movie[] testMovies =
            {
                    new Movie("Interstelar",
                              "Ceva film cu stele",
                              "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                              4.1,
                               new Date(2001,11,12)),
                    new Movie("Interstelar",
                            "Ceva film cu stele",
                            "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                            4.1,
                            new Date(2001,11,12)),
                    new Movie("Interstelar",
                            "Ceva film cu stele",
                            "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                            4.1,
                            new Date(2001,11,12)),
                    new Movie("Interstelar",
                            "Ceva film cu stele",
                            "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                            4.1,
                            new Date(2001,11,12)),
                    new Movie("Interstelar",
                            "Ceva film cu stele",
                            "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                            4.1,
                            new Date(2001,11,12)),
                    new Movie("Interstelar",
                            "Ceva film cu stele",
                            "http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                            4.1,
                            new Date(2001,11,12))
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<>(Arrays.asList(testMovies));
        }
        else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }


        movieAdapter = new MovieAdapter(this,  movieList);

        GridView gridView = findViewById(R.id.gvMovies);
        gridView.setAdapter(movieAdapter);
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

    }
    void showTopRatedMovies()
    {

    }

}
