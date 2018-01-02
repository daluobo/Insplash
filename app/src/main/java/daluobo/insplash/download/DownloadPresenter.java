package daluobo.insplash.download;

import android.os.Environment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import daluobo.insplash.common.MyApplication;
import daluobo.insplash.db.model.DownloadItem;
import daluobo.insplash.repository.DownloadRepository;
import daluobo.insplash.util.ToastUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by daluobo on 2017/12/31.
 */

public class DownloadPresenter {
    private static DownloadPresenter mInstance;

    protected DownloadRepository mRepository;
    protected Flowable<List<DownloadItem>> mDbRecord;
    protected List<DownloadItem> mDownloadItems = new ArrayList<>();
    private OnRecordChangeListener mOnRecordChangeListener;

    private DownloadPresenter() {
        mRepository = new DownloadRepository();
        mDbRecord = mRepository.getAll();

        mDbRecord.onBackpressureDrop()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DownloadItem>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<DownloadItem> downloadItems) {
                        mDownloadItems.clear();
                        mDownloadItems.addAll(downloadItems);
                        if (mOnRecordChangeListener != null) {
                            mOnRecordChangeListener.onChange(downloadItems);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static DownloadPresenter getInstance() {
        if (mInstance == null) {
            mInstance = new DownloadPresenter();
        }
        return mInstance;
    }

    public void startDownload(DownloadItem downloadItem) {
        DownloadTask downloadTask = new DownloadTask(new DownloadListener() {
            @Override
            public void onProgress(DownloadItem downloadItem, int progress) {
                downloadItem.process = progress;
            }

            @Override
            public void onSuccess(DownloadItem downloadItem, String state) {
                downloadItem.state = state;
                downloadItem.mDownloadTask = null;

                mRepository.update(downloadItem);
            }

            @Override
            public void onFailed(DownloadItem downloadItem, String state) {
                downloadItem.state = state;
                downloadItem.mDownloadTask = null;

                mRepository.update(downloadItem);
            }

            @Override
            public void onPaused(DownloadItem downloadItem, String state) {
                downloadItem.state = state;
                downloadItem.mDownloadTask = null;

                mRepository.update(downloadItem);
            }

            @Override
            public void onCanceled(DownloadItem downloadItem, String state) {
                downloadItem.state = state;
                downloadItem.mDownloadTask = null;

                mRepository.update(downloadItem);
            }
        });
        downloadItem.mDownloadTask = downloadTask;
        downloadTask.execute(downloadItem);

        downloadItem.state = DownloadState.PROCESSING;
//        if (mDbRecord.contains(downloadItem)) {
//            mRepository.update(downloadItem);
//        } else {
            mRepository.add(downloadItem);
//            mDbRecord.add(downloadItem);
//        }
        ToastUtil.showShort(MyApplication.getInstance(), "下载中");
    }

    public void pauseDownload(DownloadItem downloadItem) {
        DownloadTask downloadTask = getDownloadTask(downloadItem);

        if (downloadTask != null) {
            downloadTask.pauseDownload();
        }

    }

    public void cancelDownload(DownloadItem downloadItem) {
        DownloadTask downloadTask = getDownloadTask(downloadItem);

        if (downloadTask != null) {
            downloadTask.cancelDownload();
        }
        if (downloadItem != null) {
            String fileName = downloadItem.name;
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            File file = new File(directory + fileName);
            if (file.exists()) {
                file.delete();
            }

        }
    }

    public DownloadTask getDownloadTask(DownloadItem downloadItem) {
        for (DownloadItem item : mDownloadItems) {
            if (item.equals(downloadItem)) {
                return item.mDownloadTask;
            }
        }
        return null;
    }

    public List<DownloadItem> getDownloadItems() {
        return mDownloadItems;
    }

    public boolean isDownloading() {
        for (DownloadItem downloadItem : mDownloadItems) {
            if (downloadItem.state.equals(DownloadState.PROCESSING)) {
                return true;
            }
        }

        return false;
    }

    public OnRecordChangeListener getOnRecordChangeListener() {
        return mOnRecordChangeListener;
    }

    public void setOnRecordChangeListener(OnRecordChangeListener onRecordChangeListener) {
        mOnRecordChangeListener = onRecordChangeListener;
    }

    public interface OnRecordChangeListener {
        void onChange(List<DownloadItem> downloadItems);
    }
}
