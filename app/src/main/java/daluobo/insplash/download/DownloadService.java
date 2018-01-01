package daluobo.insplash.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import daluobo.insplash.model.DownloadItem;


public class DownloadService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(DownloadItem downloadItem) {
            DownloadManager.getInstance().startDownload(downloadItem);
        }

        public void pauseDownload(DownloadItem downloadItem) {
            DownloadManager.getInstance().pauseDownload(downloadItem);
        }

        public void cancelDownload(DownloadItem downloadItem) {
            DownloadManager.getInstance().cancelDownload(downloadItem);
        }
    }

}
