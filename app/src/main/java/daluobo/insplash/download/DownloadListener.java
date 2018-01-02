package daluobo.insplash.download;


import daluobo.insplash.db.model.DownloadItem;

public interface DownloadListener {
    void onProgress(DownloadItem downloadItem, int progress);

    void onSuccess(DownloadItem downloadItem, String state);

    void onFailed(DownloadItem downloadItem, String state);

    void onPaused(DownloadItem downloadItem, String state);

    void onCanceled(DownloadItem downloadItem, String state);
}
