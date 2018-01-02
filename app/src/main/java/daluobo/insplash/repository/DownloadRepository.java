package daluobo.insplash.repository;

import android.util.Log;

import java.util.List;

import daluobo.insplash.db.AppDatabase;
import daluobo.insplash.db.dao.DownloadDao;
import daluobo.insplash.db.model.DownloadItem;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by daluobo on 2018/1/2.
 */

public class DownloadRepository {

    DownloadDao mDownloadDao = AppDatabase.sInstance.downloadDao();


    public Flowable<List<DownloadItem>> getAll() {
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

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("DownloadRepository", ""+integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void update(DownloadItem downloadItem) {
        mDownloadDao.update(downloadItem);
    }

}
