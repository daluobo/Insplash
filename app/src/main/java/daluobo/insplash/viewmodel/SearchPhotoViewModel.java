package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.repository.SearchRepository;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchPhotoViewModel extends PhotoViewModel implements ISearchView {
    private String mQuery;
    private SearchRepository mRepository = new SearchRepository();

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        return mRepository.photos(page, mQuery);
    }

    @Override
    public String getQuery() {
        return mQuery;
    }

    @Override
    public void setQuery(String query) {
        mQuery = query;
    }
}
