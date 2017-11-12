package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.TotalStats;
import daluobo.insplash.repository.StatsRepository;

/**
 * Created by daluobo on 2017/11/1.
 */

public class StatsViewModel extends ViewModel {
    private LiveData<Resource<TotalStats>> mTotalStats;
    protected StatsRepository mRepository;

    public StatsViewModel(){
        mRepository = new StatsRepository();
    }

    public LiveData<Resource<TotalStats>> getTotalStats() {
        if (this.mTotalStats != null) {
            return mTotalStats;
        } else {
            mTotalStats = mRepository.loadTotal();
        }

        return mTotalStats;
    }
}
