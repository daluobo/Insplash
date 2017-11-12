package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/1.
 */

@Keep
public class TotalStats implements Parcelable {
    public long photos;
    public long downloads;
    public long views;
    public long likes;
    public long photographers;
    public long pixels;
    public long downloads_per_second;
    public long views_per_second;
    public long developers;
    public long applications;
    public long requests;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.photos);
        dest.writeLong(this.downloads);
        dest.writeLong(this.views);
        dest.writeLong(this.likes);
        dest.writeLong(this.photographers);
        dest.writeLong(this.pixels);
        dest.writeLong(this.downloads_per_second);
        dest.writeLong(this.views_per_second);
        dest.writeLong(this.developers);
        dest.writeLong(this.applications);
        dest.writeLong(this.requests);
    }

    public TotalStats() {
    }

    protected TotalStats(Parcel in) {
        this.photos = in.readLong();
        this.downloads = in.readLong();
        this.views = in.readLong();
        this.likes = in.readLong();
        this.photographers = in.readLong();
        this.pixels = in.readLong();
        this.downloads_per_second = in.readLong();
        this.views_per_second = in.readLong();
        this.developers = in.readLong();
        this.applications = in.readLong();
        this.requests = in.readLong();
    }

    public static final Parcelable.Creator<TotalStats> CREATOR = new Parcelable.Creator<TotalStats>() {
        @Override
        public TotalStats createFromParcel(Parcel source) {
            return new TotalStats(source);
        }

        @Override
        public TotalStats[] newArray(int size) {
            return new TotalStats[size];
        }
    };
}
