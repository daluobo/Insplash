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
        DownloadState.SUCCESS,
        DownloadState.PAUSED,
        DownloadState.FAILED,
        DownloadState.CANCELED})
public @interface DownloadState {
    String WAITING = "0";
    String PROCESSING = "1";
    String SUCCESS = "2";
    String FAILED = "3";
    String PAUSED = "4";
    String CANCELED = "5";
}
