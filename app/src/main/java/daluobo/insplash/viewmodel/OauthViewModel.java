package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Token;
import daluobo.insplash.repository.OauthRepository;

/**
 * Created by daluobo on 2017/11/8.
 */

public class OauthViewModel extends ViewModel {

    private LiveData<Resource<Token>> mTotalStats;
    protected OauthRepository mRepository;

    public OauthViewModel() {
        mRepository = new OauthRepository();
    }

    public LiveData<Resource<Token>> getToken(String code) {
        if (this.mTotalStats != null) {
            return mTotalStats;
        } else {
            mTotalStats = mRepository.getToken(code);
        }

        return mTotalStats;
    }
}
