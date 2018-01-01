package daluobo.insplash.download;


import daluobo.insplash.model.DownloadItem;

public interface DownloadListener {
    void onProgress(DownloadItem downloadItem, int progress);

    void onSuccess(DownloadItem downloadItem, int state);

    void onFailed(DownloadItem downloadItem, int state);

    void onPaused(DownloadItem downloadItem, int state);

    void onCanceled(DownloadItem downloadItem, int state);
}
