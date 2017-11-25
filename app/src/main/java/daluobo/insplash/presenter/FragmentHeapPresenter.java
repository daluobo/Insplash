package daluobo.insplash.presenter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by daluobo on 2017/11/10.
 */

public class FragmentHeapPresenter {
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private String mTopId;

    public FragmentHeapPresenter(@IdRes int containerId, @NonNull FragmentManager fragmentManager) {
        this.mContainerId = containerId;
        this.mFragmentManager = fragmentManager;
    }

    public void addFragment(@NonNull Fragment fragment, String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        transaction.add(mContainerId, fragment, tag);
        if (mTopId != null) {
            Fragment oldF = getFragment(mTopId);
            transaction.hide(oldF);
        }
        transaction.commit();

        mTopId = tag;
    }

    public Fragment getFragment(String tag) {
        return mFragmentManager.findFragmentByTag(tag);
    }

    public boolean showFragment(String tag) {
        Fragment newF = getFragment(tag);
        if (newF == null || tag.equals(mTopId)) {
            return false;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //transaction.setCustomAnimations()

        if (mTopId == null) {
            transaction.show(newF).commit();
        } else {
            Fragment oldF = getFragment(mTopId);

            transaction.hide(oldF)
                    .show(newF)
                    .commit();
            mTopId = tag;
        }
        return true;
    }
}
