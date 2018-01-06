package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.User;
import daluobo.insplash.repository.SearchRepository;
import daluobo.insplash.viewmodel.i.ISearchView;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchUserViewModel extends BasePageViewModel<User> implements ISearchView {
    private String mQuery;
    private SearchRepository mRepository= new SearchRepository();

    @Override
    public LiveData<Resource<List<User>>> loadPage(int page) {
        return mRepository.users(page, mQuery);
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
