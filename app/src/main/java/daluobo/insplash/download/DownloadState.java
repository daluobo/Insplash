package daluobo.insplash.download;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daluobo on 2018/1/2.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        DownloadState.WAITING,
        DownloadState.PROCESSING,
        DownloadState.FINISH,
        DownloadState.PAUSED,
        DownloadState.ERROR})
public @interface DownloadState {
    String WAITING = "0";
    String PROCESSING = "1";
    String FINISH = "2";
    String ERROR = "3";
    String PAUSED = "4";
}
