package daluobo.insplash.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Search;
import daluobo.insplash.repository.SearchRepository;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchCollectionViewModel extends BasePageViewModel<Collection> {
    private String mQuery;
    private SearchRepository mRepository = new SearchRepository();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return Transformations.map(mRepository.collections(page, mQuery), new Function<Resource<Search<Collection>>, Resource<List<Collection>>>() {
            @Override
            public Resource<List<Collection>> apply(Resource<Search<Collection>> input) {
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
