package daluobo.insplash.service;

import android.os.Binder;

import java.util.ArrayList;
import java.util.List;

import daluobo.insplash.model.DownloadItem;

/**
 * Created by daluobo on 2017/12/30.
 */

public class DownloadBinder extends Binder {
    private List<DownloadItem> mDownloadItems = new ArrayList<>();

    public void startDownload(DownloadItem item) {
        mDownloadItems.add(item);
    }

    public void pauseDownload(DownloadItem item) {

    }

    public void cancelDownload(DownloadItem item) {

    }
}
