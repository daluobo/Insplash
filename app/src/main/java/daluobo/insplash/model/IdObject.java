package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daluobo on 2018/1/8.
 */

public class IdObject implements Parcelable {
    public String id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        IdObject other = (IdObject) obj;

        if (id == null) {
            if (other.id == null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    public IdObject() {
    }

    protected IdObject(Parcel in) {
        this.id = in.readString();
    }

    public static final Creator<IdObject> CREATOR = new Creator<IdObject>() {
        @Override
        public IdObject createFromParcel(Parcel source) {
            return new IdObject(source);
        }

        @Override
        public IdObject[] newArray(int size) {
            return new IdObject[size];
        }
    };
}
