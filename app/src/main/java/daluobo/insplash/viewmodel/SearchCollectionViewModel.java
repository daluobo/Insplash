package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.repository.SearchRepository;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchCollectionViewModel extends BasePageViewModel<Collection> implements ISearchView{
    private String mQuery;
    private SearchRepository mRepository = new SearchRepository();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.collections(page, mQuery);
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
