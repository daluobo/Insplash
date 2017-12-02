package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/27.
 */
@Keep
public class Tag implements Parcelable {
    public String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    public Tag() {
    }

    protected Tag(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}
