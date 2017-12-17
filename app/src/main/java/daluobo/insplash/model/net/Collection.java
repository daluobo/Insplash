package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daluobo on 2017/11/12.
 */
@Keep
public class Collection implements Parcelable {
    public String id;
    public String title;
    public String description;
    public String published_at;
    public String updated_at;
    public boolean curated;
    public boolean featured;
    public int total_photos;
    @SerializedName("private")
    public boolean privateX;
    public String share_key;
    public Photo cover_photo;
    public User user;
    public Links links;
    public List<Tag> tags;
    public List<PreviewPhotos> preview_photos;
    public List<String> keywords;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.published_at);
        dest.writeString(this.updated_at);
        dest.writeByte(this.curated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.featured ? (byte) 1 : (byte) 0);
        dest.writeInt(this.total_photos);
        dest.writeByte(this.privateX ? (byte) 1 : (byte) 0);
        dest.writeString(this.share_key);
        dest.writeParcelable(this.cover_photo, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.links, flags);
        dest.writeTypedList(this.tags);
        dest.writeTypedList(this.preview_photos);
        dest.writeStringList(this.keywords);
    }

    public Collection() {
    }

    protected Collection(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.published_at = in.readString();
        this.updated_at = in.readString();
        this.curated = in.readByte() != 0;
        this.featured = in.readByte() != 0;
        this.total_photos = in.readInt();
        this.privateX = in.readByte() != 0;
        this.share_key = in.readString();
        this.cover_photo = in.readParcelable(Photo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.tags = in.createTypedArrayList(Tag.CREATOR);
        this.preview_photos = in.createTypedArrayList(PreviewPhotos.CREATOR);
        this.keywords = in.createStringArrayList();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
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
