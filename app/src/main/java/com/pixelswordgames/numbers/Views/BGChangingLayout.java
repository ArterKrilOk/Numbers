package com.pixelswordgames.numbers.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BGChangingLayout extends FrameLayout {
    public BGChangingLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public BGChangingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BGChangingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BGChangingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private int val = 255;
    private OnAnimationEnd onAnimationEnd;

    public void setOnAnimationEnd(OnAnimationEnd onAnimationEnd) {
        this.onAnimationEnd = onAnimationEnd;
    }

    private void init(){
        setBackgroundColor(Color.WHITE);
    }

    public void setTargetColor(int val) {
        ValueAnimator va = ValueAnimator.ofArgb(this.val, val);
        va.setDuration(2500);
        va.addUpdateListener(animation -> {
            int v = (Integer) animation.getAnimatedValue();
            setBackgroundColor(Color.rgb(v,v,v));
        });
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if(onAnimationEnd != null)
                    onAnimationEnd.onAnimationEnd();
            }
        });
        this.val = val;
    }

    public interface OnAnimationEnd{
        void onAnimationEnd();
    }
}
