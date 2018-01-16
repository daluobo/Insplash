package daluobo.insplash.model.net;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by daluobo on 2018/1/16.
 */
@Keep
public class TrendingFeed {
    public String next_page;
    public List<Photo> photos;
}
