package ro.go.vescan.popularmovies;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ro.go.vescan.popularmovies.model.Movie;
import ro.go.vescan.popularmovies.utils.MovieDBUtilities;

public class MovieDetail extends AppCompatActivity {
    public static String MOVIE_PARCEL = "Movie";

    private ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            populateUI((Movie)intent.getParcelableExtra(MOVIE_PARCEL));
        }


    }
    private void populateUI(Movie movie) {
      // if the activity is invoked with an invalid movie
      if (movie == null) {
        closeOnError();
        return;
      }
      // populate the ui
      //movie title
      TextView title = findViewById(R.id.movieTitle_tv);
      title.setText(movie.getTitle());
      // poster
        poster = findViewById(R.id.movie_detail_poster_iv);
        Picasso.with(this).cancelRequest(poster);
        Picasso.with(this).load(MovieDBUtilities.buildPosterUrlString(movie.getImageUrl(),
                getResources().getDimensionPixelSize(R.dimen.list_movie_poster_width))).centerCrop().fit().into(poster,new Callback() {
            @Override
            public void onSuccess() { }
            @Override
            public void onError() {
                poster.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_no_poster_available, null));
            }
        });
        //release date
        TextView releaseDate = findViewById(R.id.release_date_tv);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"); // display just the year
        releaseDate.setText(dateFormat.format(movie.getReleaseDate()) );
        //rating
        TextView rating = findViewById(R.id.rating_tv);
        rating.setText(String.format("%.1f/10", movie.getRating()));
        // synopsys
        TextView synopsys = findViewById(R.id.synopsys_tv);
        synopsys.setText(movie.getSynopsis());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
