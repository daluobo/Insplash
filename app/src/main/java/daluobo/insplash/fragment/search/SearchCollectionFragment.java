package daluobo.insplash.fragment.search;

import android.arch.lifecycle.ViewModelProviders;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.event.CollectionChangeEvent;
import daluobo.insplash.fragment.base.SearchFragment;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.viewmodel.SearchCollectionViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchCollectionFragment extends SearchFragment<List<Collection>> {

    public SearchCollectionFragment() {
    }

    public static SearchCollectionFragment newInstance() {
        SearchCollectionFragment fragment = new SearchCollectionFragment();

        return fragment;
    }

    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(SearchCollectionViewModel.class);

        mAdapter = new CollectionsAdapter(getContext(), mViewModel.getData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionChangeEvent event){
        switch (event.mAction){
            case CollectionChangeEvent.Action.UPDATE:
                mAdapter.onItemChanged(event.mCollection);
                break;
            case CollectionChangeEvent.Action.DELETE:
                mAdapter.onItemRemove(event.mCollection);
                break;
        }
    }
}
