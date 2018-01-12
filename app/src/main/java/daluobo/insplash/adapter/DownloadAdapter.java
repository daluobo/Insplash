package daluobo.insplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.util.ImgUtil;

/**
 * Created by daluobo on 2017/12/30.
 */

public class DownloadAdapter extends BaseRecyclerAdapter<DownloadInfo, DownloadAdapter.ViewHolder> {
    DecimalFormat df = new DecimalFormat("#.00");

    public DownloadAdapter(Context context, List<DownloadInfo> data) {
        super.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder viewHolder, DownloadInfo item, int position) {
        viewHolder.mName.setText(item.name);

        String length = df.format((double) item.length / (1024 * 1024)) + " M";
        if (length.startsWith(".")) {
            length = "0" + length;
        }
        viewHolder.mLength.setText(length);
        ImgUtil.loadImg(mContext, viewHolder.mPhotoView, item.previewUrl);

        viewHolder.mProgressBar.setProgress((int) (item.process * 100/item.length) );
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
        @BindView(R.id.length)
        TextView mLength;
        @BindView(R.id.delete_btn)
        ImageView mDeleteBtn;
        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mProgressBar.setMax(100);
        }
    }

    public interface OnActionListener{
        void onStartClick();
    }
}
