package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daluobo on 2017/12/19.
 */

public class LikePhoto implements Parcelable {
    public Photo photo;
    public User user;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.photo, flags);
        dest.writeParcelable(this.user, flags);
    }

    public LikePhoto() {
    }

    protected LikePhoto(Parcel in) {
        this.photo = in.readParcelable(Photo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<LikePhoto> CREATOR = new Creator<LikePhoto>() {
        @Override
        public LikePhoto createFromParcel(Parcel source) {
            return new LikePhoto(source);
        }

        @Override
        public LikePhoto[] newArray(int size) {
            return new LikePhoto[size];
        }
    };
}
