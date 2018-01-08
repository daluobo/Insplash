package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.db.dao.DownloadInfoDao;
import daluobo.insplash.db.model.DownloadInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by daluobo on 2018/1/2.
 */

public class DownloadRepository {

    DownloadInfoDao mDownloadDao = null;


    public LiveData<List<DownloadInfo>> getAll() {
        return mDownloadDao.getAll();
    }

    public void add(final DownloadInfo downloadItem) {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                mDownloadDao.add(downloadItem);
            }
        });



    }

    public void update(DownloadInfo downloadItem) {
        mDownloadDao.update(downloadItem);
    }

}
