package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ClickableTextView extends AppCompatTextView {
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
        setTextColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setTextColor(Color.GRAY);
                break;
            case MotionEvent.ACTION_UP:
                setTextColor(Color.WHITE);
                break;
        }

        return true;
    }
}
