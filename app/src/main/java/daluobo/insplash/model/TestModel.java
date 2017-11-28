package daluobo.insplash.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daluobo on 2017/11/25.
 */

public class TestModel {

    public int id;
    public String title;
    public Object description;
    public String published_at;
    public String updated_at;
    public boolean curated;
    public boolean featured;
    public int total_photos;
    @SerializedName("private")
    public boolean privateX;
    public String share_key;
    public Photo cover_photo;
    public User user;
    public Links links;
    public List<Tag> tags;
    public List<Photo> preview_photos;
    public List<String> keywords;

}
