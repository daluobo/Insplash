package daluobo.insplash.download;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

import daluobo.insplash.db.AppDatabase;
import daluobo.insplash.db.model.DownloadInfo;

public class DownloadTask {
    public static final String TAG = "DownloadTask";

    private static final int START_DOWNLOAD = 1;
    private static final int UPDATE_PROGRESS = 2;

    private Context mContext;
    private DownloadInfo mDownloadInfo;

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message message) {
            if (START_DOWNLOAD == message.what) {

                new DownloadThread().start();

                return true;
            } else if (UPDATE_PROGRESS == message.what) {

                DownloadInfo di = (DownloadInfo) message.obj;

                mDownloadInfo.process = di.process;
                //AppDatabase.get(mContext).downloadDao().update(mDownloadInfo);
                return true;
            }

            return false;
        }
    });

    public DownloadTask(Context context, DownloadInfo downloadInfo) {
        this.mContext = context;
        this.mDownloadInfo = downloadInfo;
    }

    public void start() {
        new Thread() {
            @Override
            public void run() {
                DownloadInfo di = AppDatabase.get(mContext).downloadDao().getDataById(mDownloadInfo.id);
                if (di != null) {
                    Message msg = Message.obtain();
                    msg.what = START_DOWNLOAD;
                    msg.obj = di;

                    mHandler.sendMessage(msg);
                    return;
                }

                HttpURLConnection conn = null;
                RandomAccessFile raf = null;

                try {
                    conn = HttpHelper.getInstance().createConnection(mDownloadInfo.url);
                    conn.setRequestMethod("GET");
                    conn.setUseCaches(false);

                    int length = -1;
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        length = conn.getContentLength();
                    }

                    if (length > 0) {

                        File fileDir = new File(FileHelper.mDirectory);
                        if (!fileDir.exists()) {
                            fileDir.mkdir();
                        }

                        File tempFile = new File(fileDir, mDownloadInfo.name);
                        if (!tempFile.exists()){
                            tempFile.createNewFile();
                        }

                        raf = new RandomAccessFile(tempFile, "rw");
                        raf.setLength(length);

                        mDownloadInfo.length = length;
                        mDownloadInfo.process = 0;

                        //AppDatabase.get(mContext).downloadDao().add(mDownloadInfo);

                        Message msg = Message.obtain();
                        msg.what = START_DOWNLOAD;
                        msg.obj = mDownloadInfo;

                        mHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (raf != null) {
                        try {
                            raf.close();
                        } catch (Exception e) {
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }.start();

    }

    class DownloadThread extends Thread {
        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
                conn = HttpHelper.getInstance().createConnection(mDownloadInfo.url);
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);
                conn.setRequestProperty("Range", "bytes = " + mDownloadInfo.process + "-" + mDownloadInfo.length);

                int length = -1;
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_PARTIAL || responseCode == HttpURLConnection.HTTP_OK) {
                    length = conn.getContentLength();
                } else {
                    return;
                }

                if (length > 0) {
                    File tempFile = new File(FileHelper.mDirectory + "/" + mDownloadInfo.name);
                    raf = new RandomAccessFile(tempFile, "rwd");
                    raf.seek(mDownloadInfo.process);

                    is = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];

                    int len = -1;
                    boolean finish = false;

                    while (!finish && (len = is.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);

                        if (mDownloadInfo.process + len > mDownloadInfo.length) {
                            finish = true;
                        }
                        mDownloadInfo.process += len;

                        Log.d(TAG, mDownloadInfo.process + "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (Exception e) {
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }

            }
        }
    }
}
