package daluobo.insplash.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by daluobo on 2017/12/2.
 */

public class AnimHelper {

    public static Animator createReveal(View view, int x, int y, boolean reversed, AnimatorListenerAdapter listener) {
        float hypot = (float) Math.hypot(view.getHeight(), view.getWidth());
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                view, x, y,
                startRadius,
                endRadius);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        if (listener != null) {
            animator.addListener(listener);
        }
        return animator;
    }


    public static ValueAnimator createDropDown(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = value;
                view.setLayoutParams(lp);
            }
        });
        animator.setDuration(300);
        return animator;
    }

    public static AnimationSet getAnimationSetFromBottom() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateX1 = new TranslateAnimation(RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0,
                RELATIVE_TO_SELF, 2.5f, RELATIVE_TO_SELF, 0);
        translateX1.setDuration(400);
        //translateX1.setInterpolator(new DecelerateInterpolator());
        translateX1.setStartOffset(0);

        animationSet.addAnimation(translateX1);
        animationSet.setDuration(400);

        return animationSet;
    }
}
