package ro.go.vescan.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import ro.go.vescan.popularmovies.model.Movie;
import ro.go.vescan.popularmovies.utils.MovieDBUtilities;

/**
 * Created by Cristi on 2/18/2018.
 */

public class MovieAsyncLoader extends AsyncTaskLoader<List<Movie>> {
    public final static String MOST_POPULAR_KEY = "MostPopular";
    //loader params
    private boolean mLoadMostPopular;


    public MovieAsyncLoader(Context context, final Bundle loaderArgs) {
        super(context);
        mLoadMostPopular = loaderArgs.getBoolean(MOST_POPULAR_KEY, true);// if defaultValue=false we will load top rated movies
    }

    @Override
    public void deliverResult(List<Movie> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        // build URL
        URL query;
        if (mLoadMostPopular)
            query = MovieDBUtilities.buildPopularMoviesUrl();
        else
            query = MovieDBUtilities.buildTopRatedMoviesUrl();
        // get JSON response
        try {
            String jsonResp = MovieDBUtilities.getResponseFromHttpUrl(query);
            // parse JSON response into a Movie List
            JSONObject jsonObject = new JSONObject(jsonResp);
            JSONArray jsonMovies = jsonObject.getJSONArray("results");
            if (jsonMovies.length() > 0) {
                LinkedList<Movie> aRes = new LinkedList<>();
                for (int i = 0; i < jsonMovies.length(); i++) {
                    aRes.add(new Movie(jsonMovies.getJSONObject(i)));
                }
                return aRes;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
