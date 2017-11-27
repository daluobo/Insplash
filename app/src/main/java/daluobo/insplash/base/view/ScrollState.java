package daluobo.insplash.base.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daluobo on 2017/11/27.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({ScrollState.UP, ScrollState.DOWN, ScrollState.STAY})
public @interface ScrollState {
    int UP = -1;
    int STAY = 0;
    int DOWN = 1;
}
