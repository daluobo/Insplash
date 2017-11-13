package daluobo.insplash.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by daluobo on 2017/11/12.
 */

public class ImgUtil {

    public static void loadImg(@NonNull Context context, @NonNull ImageView imageView, @NonNull String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
