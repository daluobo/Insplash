package daluobo.insplash.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.Resource;

/**
 * Created by daluobo on 2017/11/2.
 */

public abstract class NetworkResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> mResult = new MediatorLiveData<>();

    @MainThread
    protected NetworkResource() {
        final LiveData<ResultType> dbSource = new MediatorLiveData<>();
        final LiveData<ApiResponse<RequestType>> apiSource = createCall();

        mResult.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType dbResult) {
                mResult.setValue(Resource.loading(dbResult));
            }
        });

        mResult.addSource(apiSource, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> apiResponse) {
                mResult.removeSource(apiSource);
                mResult.removeSource(dbSource);

                if (apiResponse.isSuccessful()) {
                    mResult.setValue(Resource.success(convertResult(apiResponse.body)));
                } else {
                    onFetchFailed();

                    mResult.setValue(Resource.error(apiResponse.errorMessage, convertResult(apiResponse.body)));
                }
            }
        });
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @WorkerThread
    protected abstract ResultType convertResult(@NonNull RequestType item);

    @MainThread
    protected void onFetchFailed() {
    }

    // returns a LiveData that represents the resource
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return mResult;
    }
}
