package daluobo.insplash.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import daluobo.insplash.download.DownloadState;
import daluobo.insplash.download.DownloadTask;
import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2017/12/30.
 */

@Entity(tableName = "download")
public class DownloadItem {

    @PrimaryKey
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
    public int process;

    @DownloadState
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "length")
    public long length;

    @Ignore
    public DownloadTask mDownloadTask;

    public DownloadItem(Photo photo, String url) {
        this.id = photo.id;
        this.name = "insplash-" + photo.id + ".jpg";
        this.previewUrl = photo.urls.thumb;
        this.url = url;
    }

    public DownloadItem() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        DownloadItem other = (DownloadItem) obj;

        if (id == null) {
            if (other.id == null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }
}
