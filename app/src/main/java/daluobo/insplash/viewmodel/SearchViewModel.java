package daluobo.insplash.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by daluobo on 2017/12/15.
 */

public class SearchViewModel extends ViewModel {
    private MediatorLiveData<String> mQuery = new MediatorLiveData<>();

    public MediatorLiveData<String> getQuery() {
        return mQuery;
    }

    public void setQuery(String query){
        mQuery.setValue(query);
    }

}
