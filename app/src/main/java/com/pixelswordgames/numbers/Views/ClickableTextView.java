package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.pixelswordgames.numbers.R;

public class ClickableTextView extends AppCompatButton {
    public ClickableTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public ClickableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setPadding(0,0,0,0);
        setBackground(null);
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
    }

}
