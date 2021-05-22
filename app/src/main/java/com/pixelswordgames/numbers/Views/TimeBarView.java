package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TimeBarView extends View {

    public TimeBarView(Context context) {
        super(context);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private static final int HEIGHT = 40;
    private static final float RADIUS = HEIGHT / 3f;
    private static final float WIDTH = HEIGHT / 10f;
    private static final int DELAY = 10;


    private Paint innerPaint, paint;
    private long maxMillis, curMillis, time;
    private OnTimeEndListener onTimeEndListener;
    private Thread thread;
    private boolean isRunning;
    private float da, a;

    private void init(){
        innerPaint = new Paint();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        innerPaint.setColor(Color.WHITE);
        paint.setStrokeWidth(WIDTH);
        a = 255;
        da = 10f;
    }

    public void start(){
        if(thread != null)
            stop();

        thread = new Thread(() -> {
            while (isRunning) {
                if(curMillis <= maxMillis / 4f) {
                    a += da;
                    if (a > 255) {
                        a = 255;
                        da = -da;
                    }
                    if (a < 0) {
                        a = 0;
                        da = -da;
                    }

                } else a = 255;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    innerPaint.setColor(Color.argb((int)a,255,255,255));
                }
                time += DELAY;
                curMillis -= DELAY;
                if(curMillis < 0) {
                    curMillis = 0;
                    if(onTimeEndListener != null)
                        onTimeEndListener.timeOut(time);
                        stop();
                }
                invalidate();
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        isRunning = true;
        thread.start();
    }

    public void stop(){
        isRunning = false;
        if(thread != null)
            thread.interrupt();
        thread = null;
    }

    public void setMaxMillis(long maxMillis) {
        this.maxMillis = maxMillis;
        invalidate();
    }

    public void setCurMillis(long curMillis) {
        this.curMillis = curMillis;
            if(curMillis > maxMillis)
                maxMillis = curMillis;
        invalidate();
    }

    public void addMillis(long millis){
        if(curMillis + millis <= maxMillis)
            curMillis += millis;
        else
            curMillis = maxMillis;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(
                WIDTH,
                WIDTH,
                getWidth() - WIDTH,
                getHeight() - WIDTH,
                RADIUS,
                RADIUS,
                paint
        );
        float w = (getWidth() - WIDTH) * (curMillis / (float)maxMillis);
        if(w > 5)
            canvas.drawRoundRect(
                    WIDTH,
                    WIDTH,
                    w,
                    getHeight() - WIDTH,
                    RADIUS,
                    RADIUS,
                    innerPaint
            );
    }

    public long getTime() {
        return time;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, HEIGHT);
    }

    public void setOnTimeEndListener(OnTimeEndListener onTimeEndListener) {
        this.onTimeEndListener = onTimeEndListener;
    }

    public interface OnTimeEndListener {
        void timeOut(long time);
    }
}
