package daluobo.insplash.helper;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.DownloadApi;
import okhttp3.ResponseBody;

/**
 * Created by daluobo on 2017/12/22.
 */

public class DownloadHelper {
    public static final String TAG = "DownloadHelper";
    private static DownloadApi mDownloadService;

    public static void download(@NonNull LifecycleOwner owner, Context context, @NonNull final String fileUrl) {

        mDownloadService = RetrofitHelper.buildImage().create(DownloadApi.class);

        new NetworkResource<ResponseBody, ResponseBody>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mDownloadService.downloadWithUrl(fileUrl);
            }

            @Override
            protected ResponseBody convertResult(@NonNull ResponseBody item) {
                return item;
            }
        }.getAsLiveData().observe(owner, new ResourceObserver<Resource<ResponseBody>, ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody responseBody) {
                boolean writtenToDisk = writeResponseBodyToDisk(responseBody);
            }

            @Override
            protected void onError(String msg) {
                super.onError(msg);
                Log.e(TAG, "error:" + msg);
            }
        });
    }

    private static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir() + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private static String getExternalFilesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    }
}
