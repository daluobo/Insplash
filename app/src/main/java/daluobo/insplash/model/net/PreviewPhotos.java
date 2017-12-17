package daluobo.insplash.model.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/27.
 */
@Keep
public class PreviewPhotos implements Parcelable {
    public int id;
    public Urls urls;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.urls, flags);
    }

    public PreviewPhotos() {
    }

    protected PreviewPhotos(Parcel in) {
        this.id = in.readInt();
        this.urls = in.readParcelable(Urls.class.getClassLoader());
    }

    public static final Parcelable.Creator<PreviewPhotos> CREATOR = new Parcelable.Creator<PreviewPhotos>() {
        @Override
        public PreviewPhotos createFromParcel(Parcel source) {
            return new PreviewPhotos(source);
        }

        @Override
        public PreviewPhotos[] newArray(int size) {
            return new PreviewPhotos[size];
        }
    };
}
