package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TasksCountView extends View {

    public TasksCountView(Context context) {
        super(context);
        init();
    }

    public TasksCountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TasksCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TasksCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private static final int HEIGHT = 40;
    private static final float RADIUS = HEIGHT / 3f;
    private static final float WIDTH = HEIGHT / 10f;


    private Paint paint;
    private int tasks = 15, curTask = 0;

    private void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //float dx = (getWidth() - ((tasks-1)*(getWidth() / tasks) + HEIGHT)) / 2f;

        for(int i = 0; i < tasks; ++i) {
            float x = i*((float)(getWidth()-HEIGHT) / (tasks-1));

            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(
                    x + WIDTH,
                    WIDTH,
                    x + HEIGHT - WIDTH,
                    HEIGHT - WIDTH,
                    RADIUS,
                    RADIUS,
                    paint
            );
        }

        for(int i = 0; i < curTask; ++i) {
            float x = i*((float)(getWidth()-HEIGHT) / (tasks-1));

            paint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(
                    x + WIDTH,
                    WIDTH,
                    x + HEIGHT - WIDTH,
                    HEIGHT - WIDTH,
                    RADIUS,
                    RADIUS,
                    paint
            );
        }
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
        invalidate();
    }

    public void setCurTask(int curTask) {
        this.curTask = curTask;
        if(curTask > tasks)
            this.curTask = tasks;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, HEIGHT);
    }
}
