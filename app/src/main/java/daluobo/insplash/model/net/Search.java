package daluobo.insplash.model.net;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by daluobo on 2017/12/7.
 */
@Keep
public class Search<T>{
    public int total;
    public int total_pages;
    public List<T> results;
}
