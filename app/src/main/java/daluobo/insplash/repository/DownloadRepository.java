package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.db.AppDatabase;
import daluobo.insplash.db.dao.DownloadDao;
import daluobo.insplash.db.model.DownloadItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by daluobo on 2018/1/2.
 */

public class DownloadRepository {

    DownloadDao mDownloadDao = AppDatabase.sInstance.downloadDao();


    public LiveData<List<DownloadItem>> getAll() {
        return mDownloadDao.getAll();
    }

    public void add(final DownloadItem downloadItem) {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                mDownloadDao.add(downloadItem);
            }
        });



    }

    public void update(DownloadItem downloadItem) {
        mDownloadDao.update(downloadItem);
    }

}
