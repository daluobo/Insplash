package daluobo.insplash.base.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daluobo on 2017/6/8.
 */

public abstract class BaseRecyclerAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final List<D> mData = new ArrayList<>();

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final D item = getItem(position);
        if (item != null) {
            bindDataToItemView(holder, item, position);
        }
    }

    protected abstract void bindDataToItemView(VH viewHolder, D item, int position);

    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    protected D getItem(int position) {
        if (position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(D item) {
        mData.add(item); // 需要自己通知更新
    }

    public void addItems(List<D> items) {
        mData.addAll(items); // 需要自己通知更新
    }

    public void clearItems(){
        mData.clear();
    }
}
