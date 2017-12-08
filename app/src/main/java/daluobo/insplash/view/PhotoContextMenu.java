package daluobo.insplash.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/12/8.
 */

public class PhotoContextMenu extends LinearLayout {
    private static final int CONTEXT_MENU_WIDTH = DimensionUtil.dpToPx(240);
    private int ItemIndex = -1;
    private OnPhotoContextMenuItemClickListener onItemClickListener;

    public PhotoContextMenu(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_photo_menu, this, true);
        setBackground(new ColorDrawable(Color.parseColor("#000000")));
        setOrientation(VERTICAL);
        setElevation(10);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
    }

    public void dismiss() {
        ((ViewGroup) getParent()).removeView(PhotoContextMenu.this);
    }

    public void bindToItem(int item) {
        this.ItemIndex = item;
    }

    @OnClick(R.id.download_btn)
    public void onReportClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onDownloadClick(ItemIndex);
        }
    }

    @OnClick(R.id.collect_btn)
    public void onSharePhotoClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCollectClick(ItemIndex);
        }
    }

    public void setOnMenuItemClickListener(OnPhotoContextMenuItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnPhotoContextMenuItemClickListener {
        void onDownloadClick(int position);

        void onCollectClick(int position);
    }
}
