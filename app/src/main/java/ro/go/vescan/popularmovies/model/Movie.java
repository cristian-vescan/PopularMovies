package ro.go.vescan.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cristi on 2/16/2018.
 */

public class Movie implements Parcelable {
    //original title
    private String title;
    //movie poster image thumbnail
    private String image;
    // A plot synopsis (called overview in the api)
    private String synopsis;
    //user rating (called vote_average in the api)
    private Double rating;
    //release date
    private Date releaseDate;
    public Movie()
    {
    }
    public Movie(String title, String synopsys, String image, Double rating, Date releaseDate)
    {
        this.title = title;
        this.synopsis = synopsys;
        this.image = image;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
    public String getTitle() {return title;}
    public String getImageUrl() {return image;}
    public String getSynopsis() {return  synopsis;}
    public Double getRating() {return rating;}
    public Date   getReleaseDate() {return releaseDate;}

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(JSONObject fromMovieDbJson)
    {
      /*
        MovieDB Json Schema
        poster_path     string or null  optional
        overview        string          optional
        release_date    string          optional
        title           string          optional
        vote_average    number          optional
        Example
        {
         "poster_path": "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
         "overview": "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
         "release_date": "2016-08-03",
         "title": "Suicide Squad",
         "vote_average": 5.91
        }
      */
      try {
          if (!fromMovieDbJson.isNull("title")) title = fromMovieDbJson.getString("title");
          if (!fromMovieDbJson.isNull("poster_path")) image = fromMovieDbJson.getString("poster_path");
          if (!fromMovieDbJson.isNull("overview")) synopsis = fromMovieDbJson.getString("overview");
          if (!fromMovieDbJson.isNull("vote_average")) rating = fromMovieDbJson.getDouble("vote_average");
          if (!fromMovieDbJson.isNull("release_date"))
          {   // parse the String representing the release date. the format is yyyy-MM-dd
              String relDate = fromMovieDbJson.getString("release_date");
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
              releaseDate = simpleDateFormat.parse(relDate);
          }

      }
      catch (Exception e){ }
    }

    private Movie(Parcel parcel)
    {
        title = parcel.readString();
        image = parcel.readString();
        synopsis = parcel.readString();
        rating = parcel.readDouble();
        releaseDate.setTime(parcel.readLong());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(synopsis);
        parcel.writeDouble(rating);
        parcel.writeLong(releaseDate.getTime());
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
