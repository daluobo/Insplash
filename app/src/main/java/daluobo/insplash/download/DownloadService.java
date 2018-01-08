package daluobo.insplash.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import daluobo.insplash.db.model.DownloadInfo;


public class DownloadService extends Service {
    public static final String  ARG_DOWNLOAD_INFO = "arg_download_info";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_CANCEL = "action_stop";
    public static final String ACTION_UPDATE = "action_update";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            if (ACTION_START.equals(intent.getAction())) {
                DownloadInfo downloadFile = intent.getParcelableExtra(ARG_DOWNLOAD_INFO);
                start(downloadFile);
            } else if (ACTION_CANCEL.equals(intent.getAction())) {
                DownloadInfo downloadFile = intent.getParcelableExtra(ARG_DOWNLOAD_INFO);
                stop(downloadFile);
            } else if (ACTION_UPDATE.equals(intent.getAction())) {

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void start(DownloadInfo downloadFile) {
        DownloadTask dt = new DownloadTask(this,downloadFile);
        dt.start();
    }

    private void stop(DownloadInfo downloadFile) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
