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

public class LineDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int mWidth;
    private int mTop;
    private int mBottom;

    int dp1, dp2, dp3, dp4;


    public LineDecoration(Context context, int width, int top, int bottom) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        a.recycle();

        mWidth = width;
        mTop = top;
        mBottom = bottom;

        dp1 = DimensionUtil.dpToPx(mWidth);
        dp2 = DimensionUtil.dpToPx(mWidth / 2);
        dp3 = DimensionUtil.dpToPx(mTop);
        dp4 = DimensionUtil.dpToPx(mBottom);
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

        if (isFirstColumn(parent, parent.getChildAdapterPosition(view), spanCount)) {
            outRect.set(dp1, dp3, dp2, dp4);
        } else if (isLastColumn(parent, parent.getChildAdapterPosition(view), spanCount)) {
            outRect.set(dp2, dp3, dp1, dp4);
        } else {
            outRect.set(dp1, dp3, dp2, dp4);
        }

    }
}
