package ro.go.vescan.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ro.go.vescan.popularmovies.model.Movie;
import ro.go.vescan.popularmovies.utils.MovieDBUtilities;

/**
 * Created by Cristi on 2/17/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param movies         A List of Movies objects to display in a list
     */

    public MovieAdapter(Activity context, List<Movie> movies)
    {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // obtain the Movie a the required position
        Movie movie = getItem(position);
        // if this a new view inflate the layout else recycle it
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }
        final ImageView iconView = convertView.findViewById(R.id.movie_poster_iv);
        if (!movie.getImageUrl().isEmpty()) {
            // load the image from the server and CENTER CROP it into the icon view
            // getContext().getResources().getDimensionPixelSize(R.dimen.list_movie_poster_width)
            // ensures that the best image size for the current display will be selected

            iconView.setMinimumHeight(Math.round(((GridView)parent).getColumnWidth()*1.5027f));
            // cancel previous request running for this image view to prevent leaks
            Picasso.with(getContext()).cancelRequest(iconView);
            Picasso.with(getContext()).load(MovieDBUtilities.buildPosterUrlString(movie.getImageUrl(),
                    ((GridView)parent).getColumnWidth() )).centerCrop().fit().into(iconView,
                    new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError() {
                            iconView.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_no_poster_available, null));

                        }
                    }
            );
        }
        return convertView;
    }
}
