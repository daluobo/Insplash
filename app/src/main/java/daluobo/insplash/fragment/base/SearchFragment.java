package daluobo.insplash.fragment.base;

import daluobo.insplash.base.view.SimpleSwipeListFragment;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.view.LineDecoration;
import daluobo.insplash.viewmodel.i.ISearchView;

/**
 * Created by daluobo on 2017/12/7.
 */

public abstract class SearchFragment<T> extends SimpleSwipeListFragment<T> {

    @Override
    public void initView() {
        initListView();

        if (ConfigHelper.isCompatView()) {
            mListView.addItemDecoration(new LineDecoration(getContext(), 0, 4, 4));
        }
    }

    public void setQuery(String query) {
        ((ISearchView) mViewModel).setQuery(query);

        super.onShowRefresh();
        super.onRefresh();
    }

    public String getQuery() {
        return ((ISearchView) mViewModel).getQuery();
    }
}
