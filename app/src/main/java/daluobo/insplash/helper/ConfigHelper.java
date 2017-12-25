package daluobo.insplash.helper;

import android.support.annotation.IntDef;

/**
 * Created by daluobo on 2017/12/25.
 */

public class ConfigHelper {

    @IntDef({ViewType.COMPAT, ViewType.CARD})
    public  @interface ViewType {
        int COMPAT = 0;
        int CARD = 1;
    }

    public static int getViewType() {
        return ViewType.COMPAT;
    }
}
