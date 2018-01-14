package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/12/19.
 */
@Keep
public class CollectPhoto implements Parcelable {
    public Photo photo;
    public Collection collection;
    public User user;
    public String created_at;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.photo, flags);
        dest.writeParcelable(this.collection, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.created_at);
    }

    public CollectPhoto() {
    }

    protected CollectPhoto(Parcel in) {
        this.photo = in.readParcelable(Photo.class.getClassLoader());
        this.collection = in.readParcelable(Collection.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.created_at = in.readString();
    }

    public static final Parcelable.Creator<CollectPhoto> CREATOR = new Parcelable.Creator<CollectPhoto>() {
        @Override
        public CollectPhoto createFromParcel(Parcel source) {
            return new CollectPhoto(source);
        }

        @Override
        public CollectPhoto[] newArray(int size) {
            return new CollectPhoto[size];
        }
    };
}
