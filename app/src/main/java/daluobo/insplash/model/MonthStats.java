package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/1.
 */

@Keep
public class MonthStats implements Parcelable {
    public long downloads;
    public long views;
    public long likes;
    public long new_photos;
    public long new_photographers;
    public long new_pixels;
    public long new_developers;
    public long new_applications;
    public long new_requests;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.downloads);
        dest.writeLong(this.views);
        dest.writeLong(this.likes);
        dest.writeLong(this.new_photos);
        dest.writeLong(this.new_photographers);
        dest.writeLong(this.new_pixels);
        dest.writeLong(this.new_developers);
        dest.writeLong(this.new_applications);
        dest.writeLong(this.new_requests);
    }

    public MonthStats() {
    }

    protected MonthStats(Parcel in) {
        this.downloads = in.readLong();
        this.views = in.readLong();
        this.likes = in.readLong();
        this.new_photos = in.readLong();
        this.new_photographers = in.readLong();
        this.new_pixels = in.readLong();
        this.new_developers = in.readLong();
        this.new_applications = in.readLong();
        this.new_requests = in.readLong();
    }

    public static final Parcelable.Creator<MonthStats> CREATOR = new Parcelable.Creator<MonthStats>() {
        @Override
        public MonthStats createFromParcel(Parcel source) {
            return new MonthStats(source);
        }

        @Override
        public MonthStats[] newArray(int size) {
            return new MonthStats[size];
        }
    };
}
