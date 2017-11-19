package daluobo.insplash.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by daluobo on 2017/11/19.
 */

public class TopSearchBehavior<V extends View> extends VerticalScrollingBehavior<V> {
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private boolean isTablet;
    private boolean hidden = false;
    private ViewPropertyAnimatorCompat mOffsetValueAnimator;
    private boolean scrollingEnabled = true;
    int[] attrsArray = new int[]{
            android.R.attr.id};

    public TopSearchBehavior() {
        super();
    }

    public TopSearchBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static <V extends View> TopSearchBehavior<V> from(@NonNull V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof TopSearchBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with TopSearchBehavior");
        }
        return (TopSearchBehavior<V>) behavior;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        boolean layoutChild = super.onLayoutChild(parent, child, layoutDirection);
        return layoutChild;
    }

    @Override
    public void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int direction, int currentOverScroll, int totalOverScroll) {
    }

    @Override
    public void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection) {
        handleDirection(child, scrollDirection);
    }

    private void handleDirection(V child, @ScrollDirection int scrollDirection) {
        if (!scrollingEnabled) return;
        if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && hidden) {
            hidden = false;
            animateOffset(child, 0);
        } else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP && !hidden) {
            hidden = true;
            animateOffset(child, -(child.getHeight() + child.getTop()));
        }
    }

    @Override
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, @ScrollDirection int scrollDirection) {
        handleDirection(child, scrollDirection);
        return true;
    }

    private void animateOffset(final V child, final int offset) {
        ensureOrCancelAnimator(child);
        mOffsetValueAnimator.translationY(offset).start();
    }

    private void ensureOrCancelAnimator(V child) {
        if (mOffsetValueAnimator == null) {
            mOffsetValueAnimator = ViewCompat.animate(child);
            mOffsetValueAnimator.setDuration(300);
            mOffsetValueAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mOffsetValueAnimator.cancel();
        }
    }

    public boolean isScrollingEnabled() {
        return scrollingEnabled;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        this.scrollingEnabled = scrollingEnabled;
    }

    public void setHidden(V view, boolean bottomLayoutHidden) {
        if (!bottomLayoutHidden && hidden) {
            animateOffset(view, 0);
        } else if (bottomLayoutHidden && !hidden) {
            animateOffset(view, (view.getHeight() + view.getTop()));
        }
        hidden = bottomLayoutHidden;
    }
}
