package daluobo.insplash.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Search;
import daluobo.insplash.model.User;
import daluobo.insplash.repository.SearchRepository;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchUserViewModel extends BasePageViewModel<User>{
    private String mQuery;
    private SearchRepository mRepository= new SearchRepository();

    @Override
    public LiveData<Resource<List<User>>> loadPage(int page) {
        return Transformations.map(mRepository.users(page, mQuery), new Function<Resource<Search<User>>, Resource<List<User>>>() {
            @Override
            public Resource<List<User>> apply(Resource<Search<User>> input) {
                return new Resource(input.status, input.data.results, input.message);
            }
        });
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }
}
