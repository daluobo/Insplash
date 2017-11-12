package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseFragment;

/**
 * Created by daluobo on 2017/11/9.
 */

public class CollectionsFragment extends BaseFragment{
    public CollectionsFragment() {
    }

    public static CollectionsFragment newInstance() {
        CollectionsFragment fragment = new CollectionsFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.holder_empty, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
