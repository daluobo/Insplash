package daluobo.insplash.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import daluobo.insplash.db.AppDatabase;
import daluobo.insplash.db.model.DownloadInfo;


public class DownloadService extends Service {
    public static final String TAG = "DownloadService";
    public static final String ARG_DOWNLOAD_INFO = "arg_download_info";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_CANCEL = "action_stop";

    public static final List<DownloadTask> mTaskList = new ArrayList<>();
    public static final List<DownloadTask> mClearList = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (ACTION_START.equals(intent.getAction())) {
                DownloadInfo downloadFile = intent.getParcelableExtra(ARG_DOWNLOAD_INFO);
                start(downloadFile);
            } else if (ACTION_CANCEL.equals(intent.getAction())) {
                DownloadInfo downloadFile = intent.getParcelableExtra(ARG_DOWNLOAD_INFO);
                stop(downloadFile);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void start(DownloadInfo downloadFile) {
        DownloadTask dt = new DownloadTask(this, downloadFile);
        dt.start();
        downloadFile.state = DownloadState.PROCESSING;
        mTaskList.add(dt);

        if (mTaskList.size() == 1) {
            final ScheduledExecutorService updateProcessExecutor = Executors.newScheduledThreadPool(1);
            updateProcessExecutor.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            Log.d("mUpdateProcessExecutor", "update");

                            try {
                                mClearList.clear();
                                for (DownloadTask dt : mTaskList) {
                                    if (dt.getDownloadInfo().state.equals(DownloadState.PROCESSING)) {
                                        AppDatabase.sInstance.downloadDao().update(dt.getDownloadInfo());

                                        Log.d(TAG, dt.getDownloadInfo().name + ": process " + dt.getDownloadInfo().process);
                                    } else {
                                        mClearList.add(dt);
                                    }
                                }

                                mTaskList.removeAll(mClearList);
                                if (mTaskList.size() == 0) {
                                    updateProcessExecutor.shutdown();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                updateProcessExecutor.shutdown();
                            }
                        }
                    },
                    0,
                    2000,
                    TimeUnit.MILLISECONDS);

        }
    }

    private void stop(DownloadInfo downloadFile) {
        for (DownloadTask dt : mTaskList) {
            if (dt.getDownloadInfo().equals(downloadFile)) {
                dt.setStop(true);
                mTaskList.remove(dt);
                return;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
