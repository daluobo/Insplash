package daluobo.insplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by daluobo on 2017/11/8.
 */

@Keep
public class Token implements Parcelable {
    public String access_token;
    public String token_type;
    public String scope;
    public String created_at;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeString(this.scope);
        dest.writeString(this.created_at);
    }

    public Token() {
    }

    protected Token(Parcel in) {
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.scope = in.readString();
        this.created_at = in.readString();
    }

    public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
