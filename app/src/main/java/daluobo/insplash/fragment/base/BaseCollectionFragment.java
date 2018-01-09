package daluobo.insplash.fragment.base;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.event.CollectionChangeEvent;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2018/1/9.
 */

public abstract class BaseCollectionFragment extends SwipeListFragment<Collection> {

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
