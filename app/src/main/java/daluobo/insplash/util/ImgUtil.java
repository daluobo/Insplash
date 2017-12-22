package daluobo.insplash.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import daluobo.insplash.common.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by daluobo on 2017/11/12.
 */

public class ImgUtil {

    public static void loadImg(@NonNull Context context, @NonNull ImageView imageView, @NonNull String url) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImg(@NonNull Context context, @NonNull ImageView imageView, Drawable placeholder, @NonNull String url) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImgCC(@NonNull Context context, @NonNull ImageView imageView, Drawable placeholder, @NonNull String url) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .transition(withCrossFade())
                .placeholder(placeholder)
                .into(imageView);
    }
}
