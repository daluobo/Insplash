package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.download.DownloadService;
import daluobo.insplash.download.DownloadState;
import daluobo.insplash.helper.NavHelper;
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
        viewHolder.mDownloadInfo = item;

        ImgUtil.loadImg(mContext, viewHolder.mPhotoView, item.previewUrl);
        viewHolder.mName.setText(item.name);


        if (item.state.equals(DownloadState.FINISH) ||
                item.state.equals(DownloadState.ERROR)) {
            viewHolder.mActionBtn.setImageDrawable(viewHolder.mIcRefresh);
        } else if (item.state.equals(DownloadState.PROCESSING)) {
            viewHolder.mActionBtn.setImageDrawable(viewHolder.mIcPause);
        } else {
            viewHolder.mActionBtn.setImageDrawable(viewHolder.mIcPlay);
        }


        String length = df.format((double) item.length / (1024 * 1024)) + " M";
        if (length.startsWith(".")) {
            length = "0" + length;
        }
        viewHolder.mLength.setText(length);

        int process = 0;
        if (item.length != 0) {
            process = (int) (item.process * 100 / item.length);
        }

        viewHolder.mProgressBar.setProgress(process);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_download));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.photo_view)
        ImageView mPhotoView;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.length)
        TextView mLength;
        @BindView(R.id.action_btn)
        ImageView mActionBtn;
        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;
        @BindView(R.id.container)
        LinearLayout mContainer;

        @BindDrawable(R.drawable.ic_play_arrow)
        Drawable mIcPlay;
        @BindDrawable(R.drawable.ic_refresh)
        Drawable mIcRefresh;
        @BindDrawable(R.drawable.ic_pause)
        Drawable mIcPause;

        DownloadInfo mDownloadInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mProgressBar.setMax(100);

            mContainer.setOnClickListener(this);

            mActionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String action;

                    if (mDownloadInfo.state.equals(DownloadState.PROCESSING)) {
                        action = DownloadService.ACTION_PAUSE;
                    } else if (mDownloadInfo.state.equals(DownloadState.FINISH)) {
                        action = DownloadService.ACTION_RESTART;
                    } else {
                        action = DownloadService.ACTION_START;
                    }

                    NavHelper.downloadPhoto(mContext, mDownloadInfo, action);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
