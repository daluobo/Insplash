package daluobo.insplash.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Photo;
import daluobo.insplash.model.Search;
import daluobo.insplash.repository.SearchRepository;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchPhotoViewModel extends BasePageViewModel<Photo>{
    private String mQuery;
    private SearchRepository mRepository;

    public SearchPhotoViewModel(String query){
        this.mQuery = query;
        mRepository = new SearchRepository();
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        return Transformations.map(mRepository.photos(page, mQuery), new Function<Resource<Search<Photo>>, Resource<List<Photo>>>() {
            @Override
            public Resource<List<Photo>> apply(Resource<Search<Photo>> input) {
                return new Resource(input.status, input.data.results, input.message);
            }
        });
    }
}
