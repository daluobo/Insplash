package daluobo.insplash.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by daluobo on 2017/11/14.
 */

public class ViewHelper {

    public static void setDrawableLeft(TextView textView, Drawable drawable){
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
