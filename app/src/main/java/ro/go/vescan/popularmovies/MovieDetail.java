package ro.go.vescan.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import ro.go.vescan.popularmovies.model.Movie;
import ro.go.vescan.popularmovies.utils.MovieDBUtilities;

public class MovieDetail extends AppCompatActivity {
    public static final String MOVIE_PARCEL = "Movie";

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
    @SuppressLint("DefaultLocale")
    private void populateUI(Movie movie) {
      // if the activity is invoked with an invalid movie
      if (movie == null) {
        closeOnError();
        return;
      }
      // populate the ui
      final ProgressBar progressBar = findViewById(R.id.progressBar);
      //movie title
      TextView title = findViewById(R.id.movieTitle_tv);
      title.setText(movie.getTitle());
      //used calculate backdrop width
      DisplayMetrics dmetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(dmetrics);
      // poster
      poster = findViewById(R.id.movie_detail_poster_iv);
      Picasso.with(this).cancelRequest(poster);
      Picasso.with(this).load(MovieDBUtilities.buildPosterUrlString(movie.getImageUrl(),  getResources().getDimensionPixelSize(R.dimen.list_movie_poster_width), false)).
                             centerCrop().fit().into(poster, new Callback() {
            @Override
            public void onSuccess() {
              progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
              poster.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_no_poster_available, null));
            }
          });
      //release date
      TextView releaseDate = findViewById(R.id.release_date_tv);
      @SuppressLint("SimpleDateFormat")
      SimpleDateFormat dateFormat = new SimpleDateFormat("(yyyy)"); // display just the year
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
