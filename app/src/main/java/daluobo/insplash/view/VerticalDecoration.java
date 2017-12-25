package daluobo.insplash.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/6/3.
 */

public class VerticalDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int mWidth;


    public VerticalDecoration(Context context, int width) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        a.recycle();

        mWidth = width;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    private boolean isFirstColumn(RecyclerView parent, int position, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            if ((position + 1) % spanCount == 1) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

        }
        return false;
    }

    private boolean isLastColumn(RecyclerView parent, int position, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            if ((position + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {

        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int dp1 = DimensionUtil.dpToPx(mWidth);
        int dp2 = DimensionUtil.dpToPx(mWidth / 2);

        if (isFirstColumn(parent, parent.getChildAdapterPosition(view), spanCount)) {
            outRect.set(dp1, 0, dp2, 0);
        } else if (isLastColumn(parent, parent.getChildAdapterPosition(view), spanCount)) {
            outRect.set(dp2, 0, dp1, 0);
        } else {
            outRect.set(dp1, 0, dp2, 0);
        }

    }
}
