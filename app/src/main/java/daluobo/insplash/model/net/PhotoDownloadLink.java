package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daluobo on 2017/12/22.
 */

public class PhotoDownloadLink implements Parcelable {
    public String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public PhotoDownloadLink() {
    }

    protected PhotoDownloadLink(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<PhotoDownloadLink> CREATOR = new Parcelable.Creator<PhotoDownloadLink>() {
        @Override
        public PhotoDownloadLink createFromParcel(Parcel source) {
            return new PhotoDownloadLink(source);
        }

        @Override
        public PhotoDownloadLink[] newArray(int size) {
            return new PhotoDownloadLink[size];
        }
    };
}
