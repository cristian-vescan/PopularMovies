package ro.go.vescan.popularmovies.utils;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ro.go.vescan.popularmovies.BuildConfig;

/**
 * Created by Cristi on 2/18/2018.
 */

public final class MovieDBUtilities {
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String API_BASE = "https://api.themoviedb.org/3/";
    private static final String API_KEY = BuildConfig.MOVIEDB_API_KEY;
    private static final int[] POSTER_WIDTHS = {92, 154,185,342,500,780};
    private static final int[] BACKDROP_WIDTHS = {300, 780, 1280};

    private static String getBestImageSize(int reqSize, boolean isPoster)
    {
        int[] imageSizes = isPoster ? POSTER_WIDTHS : BACKDROP_WIDTHS;
        // select the size immediately larger equal to the requested size
        int i= imageSizes.length -1;
        int nSelSize = imageSizes[i];
        while(imageSizes[i] >= reqSize)
        {
            nSelSize = imageSizes[i];
            i--;
        }
        // backdrops have a placeholder for images larger than 1280 : "original"
        // original is to large to load in real time . comment this for now
        // if (!isPoster && reqSize > nSelSize) return "original"; else
        return "w" + nSelSize;
    }

    public static String buildPosterUrlString(String relativePath, int size, boolean isPoster) {
        // build the URL with the selected width
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(getBestImageSize(size,isPoster))
                .appendEncodedPath(relativePath).build();
        try {
            URL url = new URL(builtUri.toString());
            return url.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Builds the URL used to retrieve Popular movies
     *
     * @return The URL to used to query MovieDB server
     */
    public static URL buildPopularMoviesUrl()
    {
        return buildMovieDbUrl("movie/popular");
    }
    /**
     * Builds the URL used to retrieve Top rated movies
     *
     * @return The URL to used to query MovieDB server
     */
    public static URL buildTopRatedMoviesUrl()
    {
        return buildMovieDbUrl("movie/top_rated");
    }

    /**
     * Builds the URL used to query the MovieDB API
     *
     * @param apiMethod the method we want to call
     * @return The URL to used to query MovieDB server
     */
    private static URL buildMovieDbUrl(String apiMethod)
    {
        Uri builtUri = Uri.parse(API_BASE).buildUpon().appendEncodedPath(apiMethod)
                .appendQueryParameter("api_key", API_KEY).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
