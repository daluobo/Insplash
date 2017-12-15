package daluobo.insplash.fragment.search;

import daluobo.insplash.base.view.SimpleSwipeListFragment;
import daluobo.insplash.viewmodel.ISearchView;

/**
 * Created by daluobo on 2017/12/7.
 */

public abstract class SearchFragment<T> extends SimpleSwipeListFragment<T> {
    public static final String ARG_QUERY = "query";

    @Override
    public void initView() {
        super.initView();

        super.mSwipeLayout.setEnabled(false);
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
