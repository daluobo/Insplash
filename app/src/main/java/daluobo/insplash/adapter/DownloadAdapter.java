package daluobo.insplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.model.DownloadItem;

/**
 * Created by daluobo on 2017/12/30.
 */

public class DownloadAdapter extends BaseRecyclerAdapter<DownloadItem, DownloadAdapter.ViewHolder> {

    public DownloadAdapter(Context context, List<DownloadItem> data) {
        super.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder viewHolder, DownloadItem item, int position) {
        viewHolder.mName.setText(item.name);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_download));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_view)
        ImageView mPhotoView;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.action_btn)
        ImageView mActionBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
