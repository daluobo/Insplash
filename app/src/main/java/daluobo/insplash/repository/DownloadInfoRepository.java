package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.db.AppDatabase;
import daluobo.insplash.db.dao.DownloadInfoDao;
import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.download.DownloadState;

/**
 * Created by daluobo on 2018/1/2.
 */

public class DownloadInfoRepository {

    DownloadInfoDao mDownloadDao = null;

    public DownloadInfoRepository() {
        mDownloadDao = AppDatabase.sInstance.downloadDao();
    }

    public LiveData<List<DownloadInfo>> getAll() {
        return mDownloadDao.getAll();
    }

    public LiveData<List<DownloadInfo>> getProcessing() {
        return mDownloadDao.getProcessing(DownloadState.PROCESSING);
    }
}
