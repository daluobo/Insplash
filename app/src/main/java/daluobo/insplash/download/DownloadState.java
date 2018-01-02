package daluobo.insplash.download;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daluobo on 2018/1/2.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({DownloadState.PROCESSING, DownloadState.SUCCESS, DownloadState.FAILED, DownloadState.PAUSED, DownloadState.CANCELED})
public @interface DownloadState {
    String PROCESSING = "0";
    String SUCCESS = "1";
    String FAILED = "2";
    String PAUSED = "3";
    String CANCELED = "4";
}
