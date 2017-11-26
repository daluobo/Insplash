package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daluobo on 2017/11/25.
 */

public class Location implements Parcelable {
    public String title;
    public String name;
    public String city;
    public String country;
    public Position position;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeParcelable(this.position, flags);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.position = in.readParcelable(Position.class.getClassLoader());
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
