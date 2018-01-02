package daluobo.insplash.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import daluobo.insplash.db.model.DownloadItem;


public class DownloadService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(DownloadItem downloadItem) {
            DownloadPresenter.getInstance().startDownload(downloadItem);
        }

        public void pauseDownload(DownloadItem downloadItem) {
            DownloadPresenter.getInstance().pauseDownload(downloadItem);
        }

        public void cancelDownload(DownloadItem downloadItem) {
            DownloadPresenter.getInstance().cancelDownload(downloadItem);
        }
    }

}
