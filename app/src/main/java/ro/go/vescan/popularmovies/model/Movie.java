package ro.go.vescan.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

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
