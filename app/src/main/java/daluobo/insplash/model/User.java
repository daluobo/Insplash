package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/12.
 */
@Keep
public class User implements Parcelable {
    public String id;
    public String username;
    public String name;
    public String portfolio_url;
    public String bio;
    public String location;
    public String total_likes;
    public String total_photos;
    public String total_collections;

    public ProfileImage profileImage;
    public Links links;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.portfolio_url);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeString(this.total_likes);
        dest.writeString(this.total_photos);
        dest.writeString(this.total_collections);
        dest.writeParcelable(this.profileImage, flags);
        dest.writeParcelable(this.links, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.name = in.readString();
        this.portfolio_url = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.total_likes = in.readString();
        this.total_photos = in.readString();
        this.total_collections = in.readString();
        this.profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
