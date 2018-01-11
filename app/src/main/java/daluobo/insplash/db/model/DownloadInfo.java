package daluobo.insplash.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import daluobo.insplash.download.DownloadState;
import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2017/12/30.
 */
@Keep
@Entity(tableName = "download")
public class DownloadInfo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "preview_url")
    public String previewUrl;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "process")
    public long process;

    @DownloadState
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "length")
    public long length;

    public DownloadInfo(Photo photo, String url) {
        this.id = photo.id;
        this.name = "insplash-" + photo.id + ".jpg";
        this.previewUrl = photo.urls.thumb;
        this.url = url;
    }

    public DownloadInfo() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        DownloadInfo other = (DownloadInfo) obj;

        if (id == null) {
            if (other.id == null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.previewUrl);
        dest.writeString(this.url);
        dest.writeLong(this.process);
        dest.writeString(this.state);
        dest.writeLong(this.length);
    }

    protected DownloadInfo(Parcel in) {
        this.uid = in.readInt();
        this.id = in.readString();
        this.name = in.readString();
        this.previewUrl = in.readString();
        this.url = in.readString();
        this.process = in.readLong();
        this.state = in.readString();
        this.length = in.readLong();
    }

    public static final Parcelable.Creator<DownloadInfo> CREATOR = new Parcelable.Creator<DownloadInfo>() {
        @Override
        public DownloadInfo createFromParcel(Parcel source) {
            return new DownloadInfo(source);
        }

        @Override
        public DownloadInfo[] newArray(int size) {
            return new DownloadInfo[size];
        }
    };
}
