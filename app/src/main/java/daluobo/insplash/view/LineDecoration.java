package daluobo.insplash.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/6/2.
 */

public class LineDecoration extends RecyclerView.ItemDecoration {
    private final Drawable mDivider;
    private int orientation = RecyclerView.HORIZONTAL;
    private int mPaddingStart = 0;

    public LineDecoration(Context context, int orientation) {
        this.orientation = orientation;
        int[] attrs = new int[]{android.R.attr.listDivider};
        TypedArray a = context.obtainStyledAttributes(attrs);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public LineDecoration(Context context, int orientation, int paddingStart) {
        this(context, orientation);
        mPaddingStart = DimensionUtil.dip2px(context, paddingStart);
    }

    /**
     * 画线
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == RecyclerView.HORIZONTAL) {
            drawVertical(c, parent, state);
        } else if (orientation == RecyclerView.VERTICAL) {
            drawHorizontal(c, parent, state);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(mPaddingStart, top, right, bottom);
            mDivider.draw(c);
        }
    }


    /**
     * 设置条目周边的偏移量
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == RecyclerView.HORIZONTAL) {
            //画垂直线
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        } else if (orientation == RecyclerView.VERTICAL) {
            //画水平线
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
