package com.pixelswordgames.numbers.Views;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.pixelswordgames.numbers.R;

public class SolutionView extends AppCompatButton {
    public SolutionView(@NonNull Context context) {
        super(context);
        init();
    }

    public SolutionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SolutionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private int position;

    private void init(){
        setBackgroundResource(R.drawable.round_bg);
        setClickable(true);
        setFocusable(true);
        setPadding(8,8,8,8);
        setTextColor( new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed}, //1
                        new int[]{}
                },
                new int[] {
                        Color.GRAY,
                        Color.WHITE
                }
        ));
        setTextSize(24f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(20, 20, 20, 20);
        setLayoutParams(lp);
    }

    public void setSolution(String solution){
        setText(solution);
        setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.animator.clickable_animator));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
