package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2018/1/10.
 */
@Keep
public class Badge implements Parcelable {
    public String title;
    public Boolean primary;
    public String slug;
    public String link;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.primary);
        dest.writeString(this.slug);
        dest.writeString(this.link);
    }

    public Badge() {
    }

    protected Badge(Parcel in) {
        this.title = in.readString();
        this.primary = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.slug = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<Badge> CREATOR = new Parcelable.Creator<Badge>() {
        @Override
        public Badge createFromParcel(Parcel source) {
            return new Badge(source);
        }

        @Override
        public Badge[] newArray(int size) {
            return new Badge[size];
        }
    };
}
