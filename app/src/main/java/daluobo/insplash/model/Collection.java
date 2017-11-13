package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/12.
 */
@Keep
public class Collection implements Parcelable {
    public String id;
    public String title;
    public String published_at;
    public String updated_at;
    public String curated;

    public Photo cover_photo;
    public User user;
    public Links links;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.published_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.curated);
        dest.writeParcelable(this.cover_photo, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.links, flags);
    }

    public Collection() {
    }

    protected Collection(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.published_at = in.readString();
        this.updated_at = in.readString();
        this.curated = in.readString();
        this.cover_photo = in.readParcelable(Photo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel source) {
            return new Collection(source);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}
