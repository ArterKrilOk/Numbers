package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pixelswordgames.numbers.R;

public class SolutionView extends SquareFrameView {
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

    public SolutionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.solution_frame,this);
        findViewById(R.id.frameView).setBackgroundResource(R.drawable.round_bg);
        setClickable(true);
        setFocusable(true);
    }

    public void setSolution(String solution){
        ((TextView)findViewById(R.id.textView)).setText(solution);
        setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.press_me));
    }
}
