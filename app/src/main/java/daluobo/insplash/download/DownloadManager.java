package daluobo.insplash.download;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daluobo.insplash.common.MyApplication;
import daluobo.insplash.model.DownloadItem;
import daluobo.insplash.util.ToastUtil;

/**
 * Created by daluobo on 2017/12/31.
 */

public class DownloadManager {
    public final List<DownloadItem> mDownloadItems = new ArrayList<>();
    public final Map<String, DownloadTask> mDownloadTaskMap = new HashMap<>();

    private static DownloadManager mInstance = new DownloadManager();

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        return mInstance;
    }

    public void startDownload(DownloadItem downloadItem) {
        if (getDownloadTask(downloadItem) != null) {
            return;
        }

        mDownloadItems.add(downloadItem);
        DownloadTask downloadTask = new DownloadTask(new DownloadListener() {
            @Override
            public void onProgress(DownloadItem downloadItem, int progress) {
                downloadItem.process = progress;
            }

            @Override
            public void onSuccess(DownloadItem downloadItem, int state) {
                downloadItem.state = state;

                mDownloadTaskMap.put(downloadItem.id, null);
            }

            @Override
            public void onFailed(DownloadItem downloadItem, int state) {
                downloadItem.state = state;
                mDownloadTaskMap.put(downloadItem.id, null);
            }

            @Override
            public void onPaused(DownloadItem downloadItem, int state) {
                downloadItem.state = state;

                mDownloadTaskMap.put(downloadItem.id, null);
            }

            @Override
            public void onCanceled(DownloadItem downloadItem, int state) {
                downloadItem.state = state;

                mDownloadTaskMap.put(downloadItem.id, null);
            }
        });
        downloadTask.execute(downloadItem);

        mDownloadTaskMap.put(downloadItem.id, downloadTask);
        ToastUtil.showShort(MyApplication.getInstance(), "下载中");
    }

    public void pauseDownload(DownloadItem downloadItem) {
        DownloadTask downloadTask = getDownloadTask(downloadItem);

        if (downloadTask != null) {
            downloadTask.pauseDownload();
        } else {
            if (downloadItem != null) {
                String fileName = downloadItem.name;
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }

            }
        }
    }

    public void cancelDownload(DownloadItem downloadItem) {
        DownloadTask downloadTask = getDownloadTask(downloadItem);

        if (downloadTask != null) {
            downloadTask.cancelDownload();
        }
    }

    public DownloadTask getDownloadTask(DownloadItem downloadItem) {
        return mDownloadTaskMap.get(downloadItem.id);
    }
}
