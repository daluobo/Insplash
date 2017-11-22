package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by daluobo on 2017/11/12.
 */
@Keep
public class User extends ApiModel implements Parcelable {

    public String uid;
    public String id;
    public String updated_at;
    public int numeric_id;
    public String username;
    public String name;
    public String first_name;
    public String last_name;
    public String twitter_username;
    public String portfolio_url;
    public String bio;
    public String location;
    public int total_likes;
    public int total_photos;
    public int total_collections;
    public boolean followed_by_user;
    public int following_count;
    public int followers_count;
    public int downloads;
    public ProfileImage profile_image;
    public boolean completed_onboarding;
    public int uploads_remaining;
    public String instagram_username;
    public String email;
    public String badge;
    public Links links;
    public List<Photo> photos;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.id);
        dest.writeString(this.updated_at);
        dest.writeInt(this.numeric_id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.twitter_username);
        dest.writeString(this.portfolio_url);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeInt(this.total_likes);
        dest.writeInt(this.total_photos);
        dest.writeInt(this.total_collections);
        dest.writeByte(this.followed_by_user ? (byte) 1 : (byte) 0);
        dest.writeInt(this.following_count);
        dest.writeInt(this.followers_count);
        dest.writeInt(this.downloads);
        dest.writeParcelable(this.profile_image, flags);
        dest.writeByte(this.completed_onboarding ? (byte) 1 : (byte) 0);
        dest.writeInt(this.uploads_remaining);
        dest.writeString(this.instagram_username);
        dest.writeString(this.email);
        dest.writeString(this.badge);
        dest.writeParcelable(this.links, flags);
        dest.writeTypedList(this.photos);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.id = in.readString();
        this.updated_at = in.readString();
        this.numeric_id = in.readInt();
        this.username = in.readString();
        this.name = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.twitter_username = in.readString();
        this.portfolio_url = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.total_likes = in.readInt();
        this.total_photos = in.readInt();
        this.total_collections = in.readInt();
        this.followed_by_user = in.readByte() != 0;
        this.following_count = in.readInt();
        this.followers_count = in.readInt();
        this.downloads = in.readInt();
        this.profile_image = in.readParcelable(ProfileImage.class.getClassLoader());
        this.completed_onboarding = in.readByte() != 0;
        this.uploads_remaining = in.readInt();
        this.instagram_username = in.readString();
        this.email = in.readString();
        this.badge = in.readString();
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.photos = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
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
