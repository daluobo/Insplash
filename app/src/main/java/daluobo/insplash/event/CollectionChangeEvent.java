package daluobo.insplash.event;

import android.support.annotation.StringDef;

import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2018/1/8.
 */

public class CollectionChangeEvent {

    @StringDef({CollectionChangeEvent.Action.UPDATE, CollectionChangeEvent.Action.DELETE})
    public @interface Action {
        String UPDATE = "update";
        String DELETE = "delete";
    }

    public Collection mCollection;

    @Action
    public String mAction;

    public CollectionChangeEvent(Collection collection, String action) {
        mCollection = collection;
        mAction = action;
    }
}
