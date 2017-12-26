package daluobo.insplash.helper;

import android.support.annotation.StringDef;

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
        String ENGLISH = "english";
        String CHINESE = "chinese";
    }

    public static boolean isCompatView() {
        return SharePrefHelper.getViewType().equals(ViewType.COMPAT);
    }

    public static OptionItem getViewType() {
        String value = SharePrefHelper.getViewType();
        for (OptionItem menuItem : PopupMenuHelper.mViewType) {
            if (value.equals(menuItem.value)) {
                return menuItem;
            }
        }
        return PopupMenuHelper.mViewType.get(0);
    }

    public static void setViewType(String type) {
        SharePrefHelper.setViewType(type);
    }

    public static OptionItem getLanguage() {
        String value = SharePrefHelper.getLanguage();
        for (OptionItem menuItem : PopupMenuHelper.mLanguage) {
            if (value.equals(menuItem.value)) {
                return menuItem;
            }
        }
        return PopupMenuHelper.mLanguage.get(0);
    }

    public static void setLanguage(String language) {
        SharePrefHelper.setLanguage(language);
    }
}
