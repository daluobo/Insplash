package daluobo.insplash.model;

import java.util.List;

/**
 * Created by daluobo on 2017/11/25.
 */

public class TestModel {
    public String id;
    public String created_at;
    public String updated_at;
    public int width;
    public int height;
    public String color;
    //public Object slug;
    public int downloads;
    public int likes;
    public int views;
    public boolean liked_by_user;
    public String description;
    public Exif exif;
    public Location location;
    public Urls urls;
    public Links links;
    public User user;
    public List<Collection> current_user_collections;
    public List<Category> categories;

    public static class Exif {
        public String make;
        public String model;
        public String exposure_time;
        public String aperture;
        public String focal_length;
        public int iso;
    }

    public static class Location {
        public String title;
        public String name;
        public String city;
        public String country;
        public Position position;
    }

    public static class Position {
        public double latitude;
        public double longitude;
    }
}
