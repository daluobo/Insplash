package daluobo.insplash.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.StringDef;
import android.util.DisplayMetrics;

import java.util.Locale;

import daluobo.insplash.model.OptionItem;

/**
 * Created by daluobo on 2017/12/25.
 */

public class ConfigHelper {
    @StringDef({ViewType.COMPAT, ViewType.CARD})
    public @interface ViewType {
        String COMPAT = "0";
        String CARD = "1";
    }

    @StringDef({Language.ENGLISH, Language.CHINESE})
    public @interface Language {
        String ENGLISH = "en";
        String CHINESE = "zh";
    }

    public static boolean isCompatView() {
        return SharePrefHelper.getViewType().equals(ViewType.COMPAT);
    }

    public static void changeAppLanguage(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (getLanguage().value.equals(Language.CHINESE)) {
            conf.locale = Locale.CHINA;
        } else {
            conf.locale = Locale.US;
        }
        res.updateConfiguration(conf, dm);
    }

    public static OptionItem getViewType() {
        String value = SharePrefHelper.getViewType();
        for (OptionItem menuItem : PopupMenuHelper.getViewType()) {
            if (value.equals(menuItem.value)) {
                return menuItem;
            }
        }
        return PopupMenuHelper.getViewType().get(0);
    }

    public static void setViewType(String type) {
        SharePrefHelper.setViewType(type);
    }

    public static OptionItem getLanguage() {
        String value = SharePrefHelper.getLanguage();
        for (OptionItem menuItem : PopupMenuHelper.getLanguage()) {
            if (value.equals(menuItem.value)) {
                return menuItem;
            }
        }
        return PopupMenuHelper.getLanguage().get(0);
    }

    public static void setLanguage(String language) {
        SharePrefHelper.setLanguage(language);
    }
}
