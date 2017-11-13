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

public abstract class BaseRecyclerAdapter<D, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V>{
    protected List<D> mData = new ArrayList<>();

    @Override
    public void onBindViewHolder(V holder, int position) {
        final D item = getItem(position);
        bindDataToItemView(holder,item, position);
    }

    protected abstract void bindDataToItemView(V holder, D item, int position);

    protected View inflateItemView(ViewGroup viewGroupm, int layoutId){
        return LayoutInflater.from(viewGroupm.getContext()).inflate(layoutId, viewGroupm, false);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected D getItem(int position){
        return mData.get(position);
    }
}
