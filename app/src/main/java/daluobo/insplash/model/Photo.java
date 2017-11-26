package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by daluobo on 2017/11/12.
 */
@Keep
public class Photo implements Parcelable {
    public String id;
    public String created_at;
    public String updated_at;
    public int width;
    public int height;
    public String color;
    //public Object slug;
    public int downloads;
    public int likes;
    public int views;
    public boolean liked_by_user;
    public String description;
    public Exif exif;
    public Location location;
    public Urls urls;
    public Links links;
    public User user;
    public List<Collection> current_user_collections;
    public List<Category> categories;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.color);
        dest.writeInt(this.downloads);
        dest.writeInt(this.likes);
        dest.writeInt(this.views);
        dest.writeByte(this.liked_by_user ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeParcelable(this.exif, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeParcelable(this.urls, flags);
        dest.writeParcelable(this.links, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.current_user_collections);
        dest.writeTypedList(this.categories);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.color = in.readString();
        this.downloads = in.readInt();
        this.likes = in.readInt();
        this.views = in.readInt();
        this.liked_by_user = in.readByte() != 0;
        this.description = in.readString();
        this.exif = in.readParcelable(Exif.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.urls = in.readParcelable(Urls.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.current_user_collections = in.createTypedArrayList(Collection.CREATOR);
        this.categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
