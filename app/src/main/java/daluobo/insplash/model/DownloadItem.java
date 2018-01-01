package daluobo.insplash.model;

import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2017/12/30.
 */

public class DownloadItem {
    public String id;
    public String name;
    public String previewUrl;
    public String url;
    public int process;
    public int state;
    public long length;

    public DownloadItem(Photo photo, String url) {
        this.id = photo.id;
        this.name = "insplash-" + photo.id + ".jpg";
        this.previewUrl = photo.urls.thumb;
        this.url = url;
    }

    public DownloadItem(){

    }
}
