package daluobo.insplash.download;


import daluobo.insplash.db.model.DownloadInfo;

public interface DownloadListener {
    void onWaiting(DownloadInfo downloadItem, String state);

    void onProgress(DownloadInfo downloadItem, String state, int progress);

    void onSuccess(DownloadInfo downloadItem, String state);

    void onPaused(DownloadInfo downloadItem, String state);

    void onCanceled(DownloadInfo downloadItem, String state);

    void onFailed(DownloadInfo downloadItem, String state);

}
