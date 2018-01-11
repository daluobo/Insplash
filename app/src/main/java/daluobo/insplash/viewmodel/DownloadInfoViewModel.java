package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.repository.DownloadInfoRepository;

/**
 * Created by daluobo on 2018/1/11.
 */

public class DownloadInfoViewModel extends ViewModel{
    protected DownloadInfoRepository mRepository = new DownloadInfoRepository();

    public LiveData<List<DownloadInfo>> getAll() {
        return mRepository.getAll();
    }
}
