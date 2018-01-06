package daluobo.insplash.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import daluobo.insplash.model.OptionItem;

/**
 * Created by daluobo on 2017/12/26.
 */

public class SettingViewModel extends UserViewModel {
    private MediatorLiveData<OptionItem> mViewType = new MediatorLiveData<>();


    private MediatorLiveData<OptionItem> mLanguage = new MediatorLiveData<>();


    public MediatorLiveData<OptionItem> getViewType() {
        return mViewType;
    }

    public void setViewType(OptionItem viewType) {
        mViewType.setValue(viewType);
    }

    public MediatorLiveData<OptionItem> getLanguage() {
        return mLanguage;
    }

    public void setLanguage(OptionItem language) {
        mLanguage.setValue(language);
    }

}
