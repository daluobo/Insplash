package daluobo.insplash.download;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daluobo on 2018/1/7.
 */

public class FileHelper {
    public static final String mDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+"/Insplash";

    public long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();

        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }

    public static void createDir(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                //dir exists
            } else {
                //the same name file exists, can not create dir
            }
        } else {
            file.mkdir();
        }
    }
}
